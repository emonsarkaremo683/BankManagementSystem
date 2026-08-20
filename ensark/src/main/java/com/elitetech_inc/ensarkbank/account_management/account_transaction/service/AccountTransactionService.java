package com.elitetech_inc.ensarkbank.account_management.account_transaction.service;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.AccountTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.BatchTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.OtpVerifyRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.BatchTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.OtpInitiateResponse;

import java.util.List;
import java.util.Optional;

public interface AccountTransactionService {
    AccountTransactionResponse save(AccountTransactionRequest accountTransactionRequest);
    Optional<AccountTransactionResponse> findById(Long id);
    Optional<AccountTransactionResponse> findByReferenceNo(String referenceNo);
    Optional<AccountTransactionResponse> findByAccountNumber(String accountNumber);
    List<AccountTransactionResponse> findAllByAccountNumber(String accountNumber);
    List<AccountTransactionResponse> findByAccountId(Long accountId);
    List<AccountTransactionResponse> findAll();
    List<AccountTransactionResponse> findByBranchIds(List<Long> branchIds);
    OtpInitiateResponse initiateOnlineTransaction(AccountTransactionRequest atr);
    AccountTransactionResponse verifyOnlineTransaction(OtpVerifyRequest req);
    void markOtpVerified(Long otpId);
    AccountTransactionResponse reverseTransaction(Long transactionId);
    AccountTransactionResponse reverseTransactionByReferenceNo(String referenceNo);
    BatchTransactionResponse processBatch(BatchTransactionRequest request);
}
