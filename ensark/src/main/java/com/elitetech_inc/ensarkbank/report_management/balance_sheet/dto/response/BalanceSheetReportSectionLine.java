package com.elitetech_inc.ensarkbank.report_management.balance_sheet.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceSheetReportSectionLine {
    private String glCode;
    private String accountName;
    private BigDecimal amount;
}
