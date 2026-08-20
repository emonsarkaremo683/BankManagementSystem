package com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TrialBalanceReportResponse {
    private Long branchId;
    private String branchName;
    private List<TrialBalanceReportLineResponse> lines;
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
}
