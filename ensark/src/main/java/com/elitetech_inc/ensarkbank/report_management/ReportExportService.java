package com.elitetech_inc.ensarkbank.report_management;

import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportResponse;
import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportLineResponse;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportResponse;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportLineResponse;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportResponse;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportSection;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportSectionLine;

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
public class ReportExportService {

    private final SpringTemplateEngine templateEngine;

    public ReportExportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] exportLedgerToPdf(List<LedgerReportResponse> reports) {
        try {
            LedgerReportResponse report = reports.isEmpty() ? new LedgerReportResponse() : reports.get(0);
            
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
            context.setVariable("periodFrom", "Start");
            context.setVariable("periodTo", "Present");
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

    public byte[] exportLedgerToExcel(List<LedgerReportResponse> reports) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ledger Report");
            int rowNum = 0;

            for (LedgerReportResponse report : reports) {
                Row titleRow = sheet.createRow(rowNum++);
                titleRow.createCell(0).setCellValue("Branch: " + report.getBranchName());
                titleRow.createCell(1).setCellValue("Account: " + report.getAccountNumber());

                Row balanceRow = sheet.createRow(rowNum++);
                balanceRow.createCell(0).setCellValue("Opening Balance: " + report.getOpeningBalance());
                balanceRow.createCell(1).setCellValue("Closing Balance: " + report.getClosingBalance());

                Row headerRow = sheet.createRow(rowNum++);
                String[] headers = {"Date", "Particulars", "Debit", "Credit", "Balance", "Transaction ID"};
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                for (LedgerReportLineResponse line : report.getEntries()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(line.getDate() != null ? line.getDate().format(fmt) : "-");
                    row.createCell(1).setCellValue(line.getParticulars());
                    row.createCell(2).setCellValue(line.getDebit() != null ? line.getDebit().doubleValue() : 0.0);
                    row.createCell(3).setCellValue(line.getCredit() != null ? line.getCredit().doubleValue() : 0.0);
                    row.createCell(4).setCellValue(line.getBalance() != null ? line.getBalance().doubleValue() : 0.0);
                    row.createCell(5).setCellValue(line.getTransactionId());
                }
                rowNum += 2;
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Ledger Excel", e);
        }
    }

    public byte[] exportTrialBalanceToPdf(TrialBalanceReportResponse report) {
        try {
            BigDecimal difference = report.getTotalDebit().subtract(report.getTotalCredit()).abs();
            boolean isOutOfBalance = difference.compareTo(new BigDecimal("0.01")) > 0;

            List<TrialBalanceRow> lines = new ArrayList<>();
            if (report.getLines() != null) {
                for (TrialBalanceReportLineResponse line : report.getLines()) {
                    TrialBalanceRow r = new TrialBalanceRow();
                    r.setGlCode(line.getGlCode());
                    r.setAccountName(line.getAccountName());
                    r.setAccountNumber(line.getAccountNumber());
                    r.setDebit(line.getDebit() != null ? formatCurrency(line.getDebit()) : "-");
                    r.setCredit(line.getCredit() != null ? formatCurrency(line.getCredit()) : "-");
                    lines.add(r);
                }
            }

            Context context = new Context();
            context.setVariable("branchName", report.getBranchName() != null ? report.getBranchName() : "N/A");
            context.setVariable("periodFrom", "Start");
            context.setVariable("periodTo", "Present");
            context.setVariable("isOutOfBalance", isOutOfBalance);
            context.setVariable("difference", formatCurrency(difference));
            context.setVariable("lines", lines);
            context.setVariable("totalDebit", formatCurrency(report.getTotalDebit()));
            context.setVariable("totalCredit", formatCurrency(report.getTotalCredit()));

            String htmlContent = templateEngine.process("reports/trial_balance", context);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, "/");
            builder.toStream(baos);
            builder.run();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Trial Balance PDF", e);
        }
    }

    public byte[] exportTrialBalanceToExcel(TrialBalanceReportResponse report) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Trial Balance");
            int rowNum = 0;

            Row headerInfo = sheet.createRow(rowNum++);
            headerInfo.createCell(0).setCellValue("Trial Balance: " + report.getBranchName());

            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"GL Code", "Account Name", "Account Number", "Debit", "Credit"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            for (TrialBalanceReportLineResponse line : report.getLines()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(line.getGlCode());
                row.createCell(1).setCellValue(line.getAccountName());
                row.createCell(2).setCellValue(line.getAccountNumber());
                row.createCell(3).setCellValue(line.getDebit() != null ? line.getDebit().doubleValue() : 0.0);
                row.createCell(4).setCellValue(line.getCredit() != null ? line.getCredit().doubleValue() : 0.0);
            }

            Row totalRow = sheet.createRow(rowNum++);
            totalRow.createCell(1).setCellValue("TOTAL");
            totalRow.createCell(3).setCellValue(report.getTotalDebit().doubleValue());
            totalRow.createCell(4).setCellValue(report.getTotalCredit().doubleValue());

            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Trial Balance Excel", e);
        }
    }

    public byte[] exportBalanceSheetToPdf(BalanceSheetReportResponse report) {
        try {
            Context context = new Context();
            context.setVariable("branchName", report.getBranchName() != null ? report.getBranchName() : "ALL BRANCHES");
            context.setVariable("periodFrom", "Start");
            context.setVariable("periodTo", "Present");
            
            context.setVariable("assets", formatSection(report.getAssets()));
            context.setVariable("liabilities", formatSection(report.getLiabilities()));
            context.setVariable("equity", formatSection(report.getEquity()));
            
            context.setVariable("totalAssets", formatCurrency(report.getTotalAssets()));
            context.setVariable("totalLiabilitiesAndEquity", formatCurrency(report.getTotalLiabilitiesAndEquity()));

            String htmlContent = templateEngine.process("reports/balance_sheet", context);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.openhtmltopdf.pdfboxout.PdfRendererBuilder builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, "/");
            builder.toStream(baos);
            builder.run();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Balance Sheet PDF", e);
        }
    }

    private FormattedSection formatSection(BalanceSheetReportSection section) {
        if (section == null) return new FormattedSection();
        
        FormattedSection s = new FormattedSection();
        s.setTitle(section.getTitle());
        s.setTotal(formatCurrency(section.getTotal()));
        
        List<FormattedSectionLine> lines = new ArrayList<>();
        if (section.getLines() != null) {
            for (BalanceSheetReportSectionLine line : section.getLines()) {
                FormattedSectionLine l = new FormattedSectionLine();
                l.setGlCode(line.getGlCode());
                l.setAccountName(line.getAccountName());
                l.setAmount(formatCurrency(line.getAmount()));
                lines.add(l);
            }
        }
        s.setLines(lines);
        return s;
    }

    public byte[] exportBalanceSheetToExcel(BalanceSheetReportResponse report) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Balance Sheet");
            int rowNum = 0;

            Row headerInfo = sheet.createRow(rowNum++);
            headerInfo.createCell(0).setCellValue("Balance Sheet: " + report.getBranchName());

            rowNum = addBalanceSheetSectionToExcel(sheet, rowNum, report.getAssets());
            rowNum = addBalanceSheetSectionToExcel(sheet, rowNum, report.getLiabilities());
            rowNum = addBalanceSheetSectionToExcel(sheet, rowNum, report.getEquity());

            Row totalRow = sheet.createRow(rowNum++);
            totalRow.createCell(0).setCellValue("Total Assets:");
            totalRow.createCell(2).setCellValue(report.getTotalAssets().doubleValue());

            Row totalLiabRow = sheet.createRow(rowNum++);
            totalLiabRow.createCell(0).setCellValue("Total Liabilities & Equity:");
            totalLiabRow.createCell(2).setCellValue(report.getTotalLiabilitiesAndEquity().doubleValue());

            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Balance Sheet Excel", e);
        }
    }

    private int addBalanceSheetSectionToExcel(Sheet sheet, int rowNum, BalanceSheetReportSection section) {
        if (section == null) return rowNum;
        Row sectionTitleRow = sheet.createRow(rowNum++);
        sectionTitleRow.createCell(0).setCellValue(section.getTitle());

        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("GL Code");
        headerRow.createCell(1).setCellValue("Account Name");
        headerRow.createCell(2).setCellValue("Amount");

        for (BalanceSheetReportSectionLine line : section.getLines()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(line.getGlCode());
            row.createCell(1).setCellValue(line.getAccountName());
            row.createCell(2).setCellValue(line.getAmount().doubleValue());
        }

        Row sectionTotalRow = sheet.createRow(rowNum++);
        sectionTotalRow.createCell(1).setCellValue("Total " + section.getTitle());
        sectionTotalRow.createCell(2).setCellValue(section.getTotal().doubleValue());

        rowNum++;
        return rowNum;
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

    public static class TrialBalanceRow {
        private String glCode;
        private String accountName;
        private String accountNumber;
        private String debit;
        private String credit;

        public String getGlCode() { return glCode; }
        public void setGlCode(String glCode) { this.glCode = glCode; }
        public String getAccountName() { return accountName; }
        public void setAccountName(String accountName) { this.accountName = accountName; }
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public String getDebit() { return debit; }
        public void setDebit(String debit) { this.debit = debit; }
        public String getCredit() { return credit; }
        public void setCredit(String credit) { this.credit = credit; }
    }

    public static class FormattedSection {
        private String title;
        private List<FormattedSectionLine> lines = new ArrayList<>();
        private String total;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public List<FormattedSectionLine> getLines() { return lines; }
        public void setLines(List<FormattedSectionLine> lines) { this.lines = lines; }
        public String getTotal() { return total; }
        public void setTotal(String total) { this.total = total; }
    }

    public static class FormattedSectionLine {
        private String glCode;
        private String accountName;
        private String amount;

        public String getGlCode() { return glCode; }
        public void setGlCode(String glCode) { this.glCode = glCode; }
        public String getAccountName() { return accountName; }
        public void setAccountName(String accountName) { this.accountName = accountName; }
        public String getAmount() { return amount; }
        public void setAmount(String amount) { this.amount = amount; }
    }
}
