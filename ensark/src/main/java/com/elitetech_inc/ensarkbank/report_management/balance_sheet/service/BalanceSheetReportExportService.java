package com.elitetech_inc.ensarkbank.report_management.balance_sheet.service;

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
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceSheetReportExportService {

    private final SpringTemplateEngine templateEngine;

    public BalanceSheetReportExportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(BalanceSheetReportResponse report, LocalDate from, LocalDate to) {
        try {
            Context context = new Context();
            context.setVariable("branchName", report.getBranchName() != null ? report.getBranchName() : "ALL BRANCHES");
            context.setVariable("periodFrom", from != null ? from.toString() : "Start");
            context.setVariable("periodTo", to != null ? to.toString() : "Present");
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

    public byte[] generateExcel(BalanceSheetReportResponse report, LocalDate from, LocalDate to) {
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
