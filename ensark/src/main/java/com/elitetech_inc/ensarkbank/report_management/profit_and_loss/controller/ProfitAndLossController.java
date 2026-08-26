package com.elitetech_inc.ensarkbank.report_management.profit_and_loss.controller;

import com.elitetech_inc.ensarkbank.report_management.profit_and_loss.dto.response.ProfitAndLossReportResponse;
import com.elitetech_inc.ensarkbank.report_management.profit_and_loss.service.ProfitAndLossReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/reports/profit-loss", "/api/profit-loss"})
@RequiredArgsConstructor
public class ProfitAndLossController {

    private final ProfitAndLossReportService profitAndLossReportService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT', 'AUDITOR', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping
    public ResponseEntity<ProfitAndLossReportResponse> getAll() {
        return ResponseEntity.ok(profitAndLossReportService.getAll());
    }
}
