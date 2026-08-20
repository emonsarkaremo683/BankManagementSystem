package com.elitetech_inc.ensarkbank.accounting_system.journal.service;

import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.util.QrCodeGenerator;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryExportService {

    private final SpringTemplateEngine templateEngine;

    public TransactionHistoryExportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(List<JournalResponse> entries, String accountNumber,
                              String customerName, LocalDateTime fromDate, LocalDateTime toDate) {
        return generatePdf(entries, accountNumber, customerName, fromDate, toDate, null);
    }

    public byte[] generatePdf(List<JournalResponse> entries, String accountNumber,
                              String customerName, LocalDateTime fromDate, LocalDateTime toDate,
                              String password) {
        try {
            BigDecimal running = BigDecimal.ZERO;
            List<StatementRow> rows = new ArrayList<>();
            DateTimeFormatter rowFmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
            BigDecimal totalCredits = BigDecimal.ZERO;
            BigDecimal totalDebits = BigDecimal.ZERO;

            for (JournalResponse entry : entries) {
                BigDecimal amount = entry.getAmount() != null ? entry.getAmount() : BigDecimal.ZERO;
                if (entry.getEntryType() != null) {
                    if (entry.getEntryType().name().equals("CREDIT")) {
                        running = running.add(amount);
                        totalCredits = totalCredits.add(amount);
                    } else {
                        running = running.subtract(amount);
                        totalDebits = totalDebits.add(amount);
                    }
                }
                StatementRow r = new StatementRow();
                r.setDate(entry.getDate() != null ? entry.getDate().format(rowFmt) : "-");
                r.setParticulars(entry.getParticulars());
                r.setCounterparty(entry.getCounterpartyName());
                r.setType(entry.getTransactionType() != null ? entry.getTransactionType().toString() : "-");
                r.setEntryType(entry.getEntryType() != null ? entry.getEntryType().name() : "DEBIT");
                r.setAmount(formatCurrency(amount));
                r.setBalance(formatCurrency(running));
                r.setTransactionId(entry.getTransactionId());
                rows.add(r);
            }

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String period = (fromDate != null ? fromDate.format(fmt) : "Start") + " to " + (toDate != null ? toDate.format(fmt) : "Present");
            String generatedOn = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));

            String qrUrl = "https://verify.ensarkbank.com/statement?account=" + accountNumber + "&date=" + generatedOn;
            String qrBase64 = QrCodeGenerator.generateQrCodeBase64(qrUrl, 150, 150);

            Context context = new Context();
            context.setVariable("customerName", customerName != null ? customerName : "N/A");
            context.setVariable("accountNumber", accountNumber != null ? accountNumber : "All Accounts");
            context.setVariable("period", period);
            context.setVariable("generatedOn", generatedOn);
            context.setVariable("totalTransactions", entries.size());
            context.setVariable("totalCredits", formatCurrency(totalCredits));
            context.setVariable("totalDebits", formatCurrency(totalDebits));
            context.setVariable("rows", rows);
            context.setVariable("qrCodeBase64", qrBase64);

            String htmlContent = templateEngine.process("reports/statement", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, "/");
            builder.toStream(baos);
            builder.run();

            byte[] pdfBytes = baos.toByteArray();

            if (password != null && !password.isBlank()) {
                org.apache.pdfbox.pdmodel.PDDocument pdDoc = org.apache.pdfbox.pdmodel.PDDocument.load(pdfBytes);
                org.apache.pdfbox.pdmodel.encryption.AccessPermission ap = new org.apache.pdfbox.pdmodel.encryption.AccessPermission();
                org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy spp = new org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy(password, password, ap);
                spp.setEncryptionKeyLength(128);
                pdDoc.protect(spp);
                ByteArrayOutputStream encryptedBaos = new ByteArrayOutputStream();
                pdDoc.save(encryptedBaos);
                pdDoc.close();
                pdfBytes = encryptedBaos.toByteArray();
            }

            return pdfBytes;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate statement PDF", e);
        }
    }

    public byte[] generateExcel(List<JournalResponse> entries, String accountNumber,
                                String customerName, LocalDateTime fromDate, LocalDateTime toDate) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Transaction History");
            int rowNum = 0;
            rowNum = addExcelHeader(sheet, rowNum, customerName, accountNumber, fromDate, toDate);
            rowNum = addExcelSummaryRow(sheet, rowNum, entries);
            rowNum += 1;
            rowNum = addExcelTableHeader(sheet, rowNum);
            addExcelRows(sheet, rowNum, entries);

            for (int i = 0; i < 7; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel", e);
        }
    }

    private int addExcelHeader(Sheet sheet, int rowNum, String customerName, String accountNumber,
                               LocalDateTime fromDate, LocalDateTime toDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        Row row = sheet.createRow(rowNum++);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(0);
        cell.setCellValue("EnSar Bank - Transaction Statement");

        CellStyle titleStyle = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font titleFont = sheet.getWorkbook().createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        cell.setCellStyle(titleStyle);

        sheet.createRow(rowNum++).createCell(0).setCellValue("Customer: " + (customerName != null ? customerName : "N/A"));
        sheet.createRow(rowNum++).createCell(0).setCellValue("Account: " + (accountNumber != null ? accountNumber : "All Accounts"));
        sheet.createRow(rowNum++).createCell(0).setCellValue("Period: " +
                (fromDate != null ? fromDate.format(fmt) : "Start") + " to " +
                (toDate != null ? toDate.format(fmt) : "Present"));
        sheet.createRow(rowNum++).createCell(0).setCellValue("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a")));
        return rowNum;
    }

    private int addExcelSummaryRow(Sheet sheet, int rowNum, List<JournalResponse> entries) {
        BigDecimal totalCredit = entries.stream()
                .filter(e -> e.getEntryType() != null && e.getEntryType().name().equals("CREDIT"))
                .map(JournalResponse::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDebit = entries.stream()
                .filter(e -> e.getEntryType() != null && e.getEntryType().name().equals("DEBIT"))
                .map(JournalResponse::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Total Entries:");
        row.createCell(1).setCellValue(entries.size());
        row.createCell(2).setCellValue("Total Credits:");
        row.createCell(3).setCellValue(totalCredit.doubleValue());
        row.createCell(4).setCellValue("Total Debits:");
        row.createCell(5).setCellValue(totalDebit.doubleValue());
        return rowNum;
    }

    private int addExcelTableHeader(Sheet sheet, int rowNum) {
        String[] headers = {"Date", "Transaction ID", "Particulars", "Counterparty", "Type", "Entry", "Amount"};
        Row row = sheet.createRow(rowNum++);

        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < headers.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        return rowNum;
    }

    private void addExcelRows(Sheet sheet, int startRow, List<JournalResponse> entries) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        CellStyle creditStyle = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font creditFont = sheet.getWorkbook().createFont();
        creditFont.setColor(IndexedColors.DARK_GREEN.getIndex());
        creditStyle.setFont(creditFont);

        CellStyle debitStyle = sheet.getWorkbook().createCellStyle();
        org.apache.poi.ss.usermodel.Font debitFont = sheet.getWorkbook().createFont();
        debitFont.setColor(IndexedColors.DARK_RED.getIndex());
        debitStyle.setFont(debitFont);

        int rowNum = startRow;
        for (JournalResponse e : entries) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(e.getDate() != null ? e.getDate().format(fmt) : "-");
            row.createCell(1).setCellValue(e.getTransactionId() != null ? e.getTransactionId() : "-");
            row.createCell(2).setCellValue(e.getParticulars() != null ? e.getParticulars() : "-");
            row.createCell(3).setCellValue(e.getCounterpartyName() != null ? e.getCounterpartyName() : "-");
            row.createCell(4).setCellValue(e.getTransactionType() != null ? e.getTransactionType().toString() : "-");

            String entryType = e.getEntryType() != null ? e.getEntryType().toString() : "-";
            org.apache.poi.ss.usermodel.Cell entryCell = row.createCell(5);
            entryCell.setCellValue(entryType);
            entryCell.setCellStyle("CREDIT".equals(entryType) ? creditStyle : debitStyle);

            org.apache.poi.ss.usermodel.Cell amountCell = row.createCell(6);
            amountCell.setCellValue(e.getAmount() != null ? e.getAmount().doubleValue() : 0);
            amountCell.setCellStyle("CREDIT".equals(entryType) ? creditStyle : debitStyle);
        }
    }

    public void generateCsv(List<JournalResponse> entries, Writer writer) {
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");

            String[] header = {"Date", "Transaction ID", "Particulars", "Account Number",
                    "Counterparty Account", "Counterparty Name", "Entry Type", "Amount",
                    "Transaction Type", "Channel", "Status", "Remarks"};
            csvWriter.writeNext(header);

            for (JournalResponse e : entries) {
                String[] row = {
                        e.getDate() != null ? e.getDate().format(fmt) : "-",
                        e.getTransactionId() != null ? e.getTransactionId() : "-",
                        e.getParticulars() != null ? e.getParticulars() : "-",
                        e.getAccountNumber() != null ? e.getAccountNumber() : "-",
                        e.getCounterpartyAccountNumber() != null ? e.getCounterpartyAccountNumber() : "-",
                        e.getCounterpartyName() != null ? e.getCounterpartyName() : "-",
                        e.getEntryType() != null ? e.getEntryType().toString() : "-",
                        e.getAmount() != null ? e.getAmount().toString() : "0",
                        e.getTransactionType() != null ? e.getTransactionType().toString() : "-",
                        e.getChannel() != null ? e.getChannel().toString() : "-",
                        e.getStatus() != null ? e.getStatus().toString() : "-",
                        e.getRemarks() != null ? e.getRemarks() : "-"
                };
                csvWriter.writeNext(row);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV", e);
        }
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return "$0.00";
        return String.format("$%,.2f", amount);
    }

    public static class StatementRow {
        private String date;
        private String particulars;
        private String counterparty;
        private String type;
        private String entryType;
        private String amount;
        private String balance;
        private String transactionId;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getParticulars() { return particulars; }
        public void setParticulars(String particulars) { this.particulars = particulars; }
        public String getCounterparty() { return counterparty; }
        public void setCounterparty(String counterparty) { this.counterparty = counterparty; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getEntryType() { return entryType; }
        public void setEntryType(String entryType) { this.entryType = entryType; }
        public String getAmount() { return amount; }
        public void setAmount(String amount) { this.amount = amount; }
        public String getBalance() { return balance; }
        public void setBalance(String balance) { this.balance = balance; }
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    }
}
