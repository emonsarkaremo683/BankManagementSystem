package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.service;

import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionResponse;

public interface CashierTransactionService {

    CashierTransactionResponse create(CashierTransactionRequest request);

    CashierTransactionResponse reverse(Long transactionId);
}
