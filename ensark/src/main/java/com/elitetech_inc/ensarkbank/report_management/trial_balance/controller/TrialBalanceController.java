package com.elitetech_inc.ensarkbank.report_management.trial_balance.controller;

import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportResponse;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.service.TrialBalanceReportService;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.service.TrialBalanceReportExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping({"/api/reports/trial-balance", "/api/trial-balance"})
@RequiredArgsConstructor
public class TrialBalanceController {

    private final TrialBalanceReportService trialBalanceReportService;
    private final TrialBalanceReportExportService trialBalanceReportExportService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping
    public ResponseEntity<TrialBalanceReportResponse> getAll() {
        return ResponseEntity.ok(trialBalanceReportService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("branch/{branchId}")
    public ResponseEntity<TrialBalanceReportResponse> findByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(trialBalanceReportService.findByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @PostMapping("branch/{branchId}/span")
    public ResponseEntity<TrialBalanceReportResponse> findByBranchIdAndTimeSpan(
            @PathVariable Long branchId,
            @RequestBody Map<String, String> request) {
        LocalDate from = LocalDate.parse(request.get("from"));
        LocalDate to = LocalDate.parse(request.get("to"));
        return ResponseEntity.ok(trialBalanceReportService.findByBranchIdAndTimeSpan(branchId, from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @PostMapping("span")
    public ResponseEntity<TrialBalanceReportResponse> getAllByTimeSpan(@RequestBody Map<String, String> request) {
        LocalDate from = LocalDate.parse(request.get("from"));
        LocalDate to = LocalDate.parse(request.get("to"));
        return ResponseEntity.ok(trialBalanceReportService.getAllByTimeSpan(from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER')")
    @GetMapping("export")
    public ResponseEntity<org.springframework.core.io.InputStreamResource> export(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam String format) {

        byte[] bytes = "pdf".equalsIgnoreCase(format)
                ? trialBalanceReportService.exportToPdf(branchId, from, to)
                : trialBalanceReportService.exportToExcel(branchId, from, to);

        String filename = "trial_balance." + ("pdf".equalsIgnoreCase(format) ? "pdf" : "xlsx");
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

        TrialBalanceReportResponse report = (branchId != null)
                ? trialBalanceReportService.findByBranchIdAndTimeSpan(branchId, from, to)
                : trialBalanceReportService.getAllByTimeSpan(from, to);

        byte[] pdfBytes = trialBalanceReportExportService.generatePdf(report, from, to);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"trial-balance.pdf\"")
                .body(pdfBytes);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate to) {

        TrialBalanceReportResponse report = (branchId != null)
                ? trialBalanceReportService.findByBranchIdAndTimeSpan(branchId, from, to)
                : trialBalanceReportService.getAllByTimeSpan(from, to);

        byte[] excelBytes = trialBalanceReportExportService.generateExcel(report, from, to);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"trial-balance.xlsx\"")
                .body(excelBytes);
    }
}
