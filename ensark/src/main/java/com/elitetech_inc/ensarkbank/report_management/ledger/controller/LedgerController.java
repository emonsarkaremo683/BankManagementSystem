package com.elitetech_inc.ensarkbank.report_management.ledger.controller;

import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportResponse;
import com.elitetech_inc.ensarkbank.report_management.ledger.service.LedgerReportService;
import com.elitetech_inc.ensarkbank.report_management.ledger.service.LedgerReportExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/reports/ledger", "/api/ledger"})
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerReportService ledgerReportService;
    private final LedgerReportExportService ledgerReportExportService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping
    public ResponseEntity<List<LedgerReportResponse>> getAll() {
        return ResponseEntity.ok(ledgerReportService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<LedgerReportResponse>> findByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(ledgerReportService.findByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @PostMapping("branch/{branchId}/span")
    public ResponseEntity<List<LedgerReportResponse>> findByBranchIdAndTimeSpan(
            @PathVariable Long branchId,
            @RequestBody Map<String, String> request) {
        LocalDate from = LocalDate.parse(request.get("from"));
        LocalDate to = LocalDate.parse(request.get("to"));
        return ResponseEntity.ok(ledgerReportService.findByBranchIdAndTimeSpan(branchId, from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @PostMapping("span")
    public ResponseEntity<List<LedgerReportResponse>> getAllByTimeSpan(@RequestBody Map<String, String> request) {
        LocalDate from = LocalDate.parse(request.get("from"));
        LocalDate to = LocalDate.parse(request.get("to"));
        return ResponseEntity.ok(ledgerReportService.getAllByTimeSpan(from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER')")
    @GetMapping("export")
    public ResponseEntity<org.springframework.core.io.InputStreamResource> export(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam String format) {

        byte[] bytes = "pdf".equalsIgnoreCase(format)
                ? ledgerReportService.exportToPdf(branchId, from, to)
                : ledgerReportService.exportToExcel(branchId, from, to);

        String filename = "ledger_report." + ("pdf".equalsIgnoreCase(format) ? "pdf" : "xlsx");
        String contentType = "pdf".equalsIgnoreCase(format) ? "application/pdf" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .body(new org.springframework.core.io.InputStreamResource(new java.io.ByteArrayInputStream(bytes)));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("export/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to) {

        List<LedgerReportResponse> ledgerList = (branchId != null)
                ? ledgerReportService.findByBranchIdAndTimeSpan(branchId, from, to)
                : ledgerReportService.getAllByTimeSpan(from, to);

        LedgerReportResponse report = ledgerList.isEmpty() ? new LedgerReportResponse() : ledgerList.get(0);
        byte[] pdfBytes = ledgerReportExportService.generatePdf(report, from, to);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ledger-report.pdf\"")
                .body(pdfBytes);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to) {

        List<LedgerReportResponse> ledgerList = (branchId != null)
                ? ledgerReportService.findByBranchIdAndTimeSpan(branchId, from, to)
                : ledgerReportService.getAllByTimeSpan(from, to);

        LedgerReportResponse report = ledgerList.isEmpty() ? new LedgerReportResponse() : ledgerList.get(0);
        byte[] excelBytes = ledgerReportExportService.generateExcel(report, from, to);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ledger-report.xlsx\"")
                .body(excelBytes);
    }
}
