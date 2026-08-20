package com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.common.enums.ATMTransactionType;
import lombok.Data;

@Data
public class ATMTransactionRequest {
    private Long atmId;
    private String cardNumber;
    private ATMTransactionType transactionType;
    private String pin;
    private TransactionRequest  transactionRequest;
}
