package com.elitetech_inc.ensarkbank.account_management.credit_account.service;

import com.elitetech_inc.ensarkbank.account_management.credit_account.entity.CreditAccount;

import java.math.BigDecimal;

public interface CreditPaymentService {
    CreditAccount makePayment(Long creditAccountId, Long sourceDepositAccountId, BigDecimal amount);
}
