package com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceSheetReportResponse {
    private Long branchId;
    private String branchName;
    private BalanceSheetReportSection assets;
    private BalanceSheetReportSection liabilities;
    private BalanceSheetReportSection equity;
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilitiesAndEquity;
}
