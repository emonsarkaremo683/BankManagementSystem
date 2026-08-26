package com.elitetech_inc.ensarkbank.account_management.account.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AccountStatementResponse {
    private String accountNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private int totalTransactions;
    private List<AccountStatementTransaction> transactions;
}
