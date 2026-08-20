package com.elitetech_inc.ensarkbank.report_management.ledger.service;

import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportResponse;

import java.time.LocalDate;
import java.util.List;

public interface LedgerReportService {

    List<LedgerReportResponse> getAll();

    List<LedgerReportResponse> findByBranchId(Long branchId);

    List<LedgerReportResponse> findByBranchIdAndTimeSpan(Long branchId, LocalDate from, LocalDate to);

    List<LedgerReportResponse> getAllByTimeSpan(LocalDate from, LocalDate to);

    byte[] exportToPdf(Long branchId, LocalDate from, LocalDate to);

    byte[] exportToExcel(Long branchId, LocalDate from, LocalDate to);
}
