package com.elitetech_inc.ensarkbank.report_management.balance_sheet.service;

import com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response.BalanceSheetReportResponse;

import java.time.LocalDate;

public interface BalanceSheetReportService {

    BalanceSheetReportResponse getAll();

    BalanceSheetReportResponse getAllByTimeSpan(LocalDate from, LocalDate to);

    byte[] exportToPdf(LocalDate from, LocalDate to);

    byte[] exportToExcel(LocalDate from, LocalDate to);
}
