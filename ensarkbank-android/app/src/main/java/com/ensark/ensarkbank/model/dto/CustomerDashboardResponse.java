package com.ensark.ensarkbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDashboardResponse {
    private BigDecimal balance;
    private BigDecimal totalCredit;
    private BigDecimal totalDebit;
    private BigDecimal totalLoan;
    private Integer totalCard;
    private Long totalTransaction;
    private Integer totalBeneficiary;
    private Integer totalAccount;
    private List<CardResponse> cards;
    private List<AccountResponse> accounts;
    private List<JournalResponse> last30DaysTransactions;
    private List<JournalResponse> recentTransactions;
}
