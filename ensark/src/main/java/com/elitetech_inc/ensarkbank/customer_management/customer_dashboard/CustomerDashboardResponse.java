package com.elitetech_inc.ensarkbank.customer_management.customer_dashboard;

import com.elitetech_inc.ensarkbank.account_management.account.dto.response.AccountResponse;
import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
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
