package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CashierTransactionResponse {

    private Long id;
    private Long transactionEntityId;
    private String checkNo;
    private String cashierName;
    private String branchName;

    private TransactionResponse transaction;
    private List<JournalResponse> journals;
}
