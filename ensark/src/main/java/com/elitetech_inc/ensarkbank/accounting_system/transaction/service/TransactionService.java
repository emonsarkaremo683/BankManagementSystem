package com.elitetech_inc.ensarkbank.accounting_system.transaction.service;

import org.springframework.stereotype.Service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest tr, Transaction t,
                                          String senderAccount,
                                          String receiverAccount);
    TransactionResponse reverseTransaction(Long transactionId);
    Optional<Transaction> findByTransactionId(String transactionId);
        TransactionResponse loanRepayment(TransactionRequest request, Account customerAccount, Account loanControlAccount, BigDecimal interest, BigDecimal principle);
}
