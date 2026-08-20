package com.elitetech_inc.ensarkbank.report_management.trial_balance.service;

import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportResponse;

import java.time.LocalDate;

public interface TrialBalanceReportService {

    TrialBalanceReportResponse getAll();

    TrialBalanceReportResponse findByBranchId(Long branchId);

    TrialBalanceReportResponse findByBranchIdAndTimeSpan(Long branchId, LocalDate from, LocalDate to);

    TrialBalanceReportResponse getAllByTimeSpan(LocalDate from, LocalDate to);

    byte[] exportToPdf(Long branchId, LocalDate from, LocalDate to);

    byte[] exportToExcel(Long branchId, LocalDate from, LocalDate to);
}
