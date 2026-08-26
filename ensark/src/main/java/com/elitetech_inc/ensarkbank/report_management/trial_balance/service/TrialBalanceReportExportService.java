package com.elitetech_inc.ensarkbank.report_management.trial_balance.service;

import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportResponse;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportLineResponse;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class TrialBalanceReportExportService {

    private final SpringTemplateEngine templateEngine;

    public TrialBalanceReportExportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(TrialBalanceReportResponse report, LocalDate from, LocalDate to) {
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
            context.setVariable("periodFrom", from != null ? from.toString() : "Start");
            context.setVariable("periodTo", to != null ? to.toString() : "Present");
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

    public byte[] generateExcel(TrialBalanceReportResponse report, LocalDate from, LocalDate to) {
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

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Trial Balance Excel", e);
        }
    }

    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return "$0.00";
        return String.format("$%,.2f", amount);
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
}
