package com.elitetech_inc.ensarkbank.report_management.balance_sheet.controller;

import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportResponse;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.service.BalanceSheetReportService;
import com.elitetech_inc.ensarkbank.report_management.balance_sheet.service.BalanceSheetReportExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping({"/api/reports/balance-sheet", "/api/balance-sheet"})
@RequiredArgsConstructor
public class BalanceSheetController {

    private final BalanceSheetReportService balanceSheetReportService;
    private final BalanceSheetReportExportService balanceSheetReportExportService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping
    public ResponseEntity<BalanceSheetReportResponse> getAll() {
        return ResponseEntity.ok(balanceSheetReportService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @PostMapping("span")
    public ResponseEntity<BalanceSheetReportResponse> getAllByTimeSpan(@RequestBody Map<String, String> request) {
        LocalDate from = LocalDate.parse(request.get("from"));
        LocalDate to = LocalDate.parse(request.get("to"));
        return ResponseEntity.ok(balanceSheetReportService.getAllByTimeSpan(from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER')")
    @GetMapping("export")
    public ResponseEntity<org.springframework.core.io.InputStreamResource> export(
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam String format) {

        byte[] bytes = "pdf".equalsIgnoreCase(format)
                ? balanceSheetReportService.exportToPdf(from, to)
                : balanceSheetReportService.exportToExcel(from, to);

        String filename = "balance_sheet." + ("pdf".equalsIgnoreCase(format) ? "pdf" : "xlsx");
        String contentType = "pdf".equalsIgnoreCase(format) ? "application/pdf" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .body(new org.springframework.core.io.InputStreamResource(new java.io.ByteArrayInputStream(bytes)));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("export/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to) {

        BalanceSheetReportResponse report = balanceSheetReportService.getAllByTimeSpan(from, to);
        byte[] pdfBytes = balanceSheetReportExportService.generatePdf(report, from, to);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"balance-sheet.pdf\"")
                .body(pdfBytes);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to) {

        BalanceSheetReportResponse report = balanceSheetReportService.getAllByTimeSpan(from, to);
        byte[] excelBytes = balanceSheetReportExportService.generateExcel(report, from, to);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"balance-sheet.xlsx\"")
                .body(excelBytes);
    }
}
