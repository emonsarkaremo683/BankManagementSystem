package com.elitetech_inc.ensarkbank.report_management.profit_and_loss.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfitAndLossLine {
    private String accountNumber;
    private String accountName;
    private BigDecimal amount;
}
