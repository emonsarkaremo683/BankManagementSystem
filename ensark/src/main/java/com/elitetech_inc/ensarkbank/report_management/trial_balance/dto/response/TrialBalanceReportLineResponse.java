package com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrialBalanceReportLineResponse {
    private String glCode;
    private String accountName;
    private String accountNumber;
    private BigDecimal debit;
    private BigDecimal credit;
}
