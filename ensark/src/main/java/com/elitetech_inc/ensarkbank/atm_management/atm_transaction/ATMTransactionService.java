package com.elitetech_inc.ensarkbank.atm_management.atm_transaction;

import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ATMTransactionService {
    ATMTransactionResponse transaction(ATMTransactionRequest request);
    ATMTransactionResponse refill(Long atmId, BigDecimal amount);
    List<ATMTransactionResponse> getAll();
    ATMTransactionResponse getById(Long id);
    List<ATMTransactionResponse> getByAtmId(Long atmId);
    BigDecimal checkBalance(String cardNumber, String pin);
    List<ATMTransactionResponse> getByCardNumber(String cardNumber);
    List<ATMTransactionResponse> getByDateRange(java.time.LocalDate from, java.time.LocalDate to);
}
