package com.elitetech_inc.ensarkbank.report_management.ledger.service;

import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportResponse;
import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportLineResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerReportExportService {

    private final SpringTemplateEngine templateEngine;

    public LedgerReportExportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(LedgerReportResponse report, LocalDate from, LocalDate to) {
        try {
            List<LedgerRow> entries = new ArrayList<>();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

            if (report.getEntries() != null) {
                for (LedgerReportLineResponse line : report.getEntries()) {
                    LedgerRow r = new LedgerRow();
                    r.setDate(line.getDate() != null ? line.getDate().format(fmt) : "-");
                    String details = (line.getParticulars() != null ? line.getParticulars() : "") + 
                                     (line.getAccountName() != null ? " / " + line.getAccountName() : "");
                    r.setParticulars(details);
                    r.setDebit(line.getDebit() != null && line.getDebit().compareTo(BigDecimal.ZERO) > 0 ? formatCurrency(line.getDebit()) : "-");
                    r.setCredit(line.getCredit() != null && line.getCredit().compareTo(BigDecimal.ZERO) > 0 ? formatCurrency(line.getCredit()) : "-");
                    r.setBalance(formatCurrency(line.getBalance()));
                    r.setTransactionId(line.getTransactionId() != null ? line.getTransactionId() : "-");
                    entries.add(r);
                }
            }

            Context context = new Context();
            context.setVariable("branchName", report.getBranchName() != null ? report.getBranchName() : "N/A");
            context.setVariable("accountNumber", report.getAccountNumber() != null ? report.getAccountNumber() : "N/A");
            context.setVariable("openingBalance", formatCurrency(report.getOpeningBalance()));
            context.setVariable("closingBalance", formatCurrency(report.getClosingBalance()));
            context.setVariable("periodFrom", from != null ? from.toString() : "Start");
            context.setVariable("periodTo", to != null ? to.toString() : "Present");
            context.setVariable("entries", entries);

            String htmlContent = templateEngine.process("reports/ledger", context);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, "/");
            builder.toStream(baos);
            builder.run();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Ledger PDF", e);
        }
    }

    public byte[] generateExcel(LedgerReportResponse report, LocalDate from, LocalDate to) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ledger Report");
            int rowNum = 0;

            Row titleRow = sheet.createRow(rowNum++);
            titleRow.createCell(0).setCellValue("EnSark Bank - General Ledger Report");

            sheet.createRow(rowNum++).createCell(0).setCellValue("Branch: " + (report.getBranchName() != null ? report.getBranchName() : "N/A"));
            sheet.createRow(rowNum++).createCell(0).setCellValue("Account: " + (report.getAccountNumber() != null ? report.getAccountNumber() : "N/A"));
            sheet.createRow(rowNum++).createCell(0).setCellValue("Period: " + (from != null ? from.toString() : "Start") + " to " + (to != null ? to.toString() : "Present"));
            sheet.createRow(rowNum++).createCell(0).setCellValue("Opening Balance: " + (report.getOpeningBalance() != null ? report.getOpeningBalance().doubleValue() : 0.0));
            sheet.createRow(rowNum++).createCell(0).setCellValue("Closing Balance: " + (report.getClosingBalance() != null ? report.getClosingBalance().doubleValue() : 0.0));

            rowNum++;

            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"Date", "Particulars/Account", "Debit", "Credit", "Balance", "Transaction ID"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            DateTimeFormatter rowFmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
            for (LedgerReportLineResponse line : report.getEntries()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(line.getDate() != null ? line.getDate().format(rowFmt) : "-");
                String details = (line.getParticulars() != null ? line.getParticulars() : "") + 
                                 (line.getAccountName() != null ? " / " + line.getAccountName() : "");
                row.createCell(1).setCellValue(details);
                row.createCell(2).setCellValue(line.getDebit() != null ? line.getDebit().doubleValue() : 0.0);
                row.createCell(3).setCellValue(line.getCredit() != null ? line.getCredit().doubleValue() : 0.0);
                row.createCell(4).setCellValue(line.getBalance() != null ? line.getBalance().doubleValue() : 0.0);
                row.createCell(5).setCellValue(line.getTransactionId() != null ? line.getTransactionId() : "-");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Ledger Excel", e);
        }
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return "$0.00";
        return String.format("$%,.2f", amount);
    }

    public static class LedgerRow {
        private String date;
        private String particulars;
        private String debit;
        private String credit;
        private String balance;
        private String transactionId;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getParticulars() { return particulars; }
        public void setParticulars(String particulars) { this.particulars = particulars; }
        public String getDebit() { return debit; }
        public void setDebit(String debit) { this.debit = debit; }
        public String getCredit() { return credit; }
        public void setCredit(String credit) { this.credit = credit; }
        public String getBalance() { return balance; }
        public void setBalance(String balance) { this.balance = balance; }
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    }
}
