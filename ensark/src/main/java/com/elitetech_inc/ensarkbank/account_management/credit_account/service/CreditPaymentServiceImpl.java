package com.elitetech_inc.ensarkbank.account_management.credit_account.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.credit_account.entity.CreditAccount;
import com.elitetech_inc.ensarkbank.account_management.credit_account.repository.CreditAccountRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.service.TransactionPostingService;
import com.elitetech_inc.ensarkbank.common.enums.*;
import com.elitetech_inc.ensarkbank.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditPaymentServiceImpl implements CreditPaymentService {

    private final CreditAccountRepository creditAccountRepository;
    private final AccountRepository accountRepository;
    private final TransactionPostingService transactionPostingService;
    private final TransactionRepository transactionRepository;
    private final EntityManager entityManager;
    private final Utils utils;

    @Override
    @Transactional
    public CreditAccount makePayment(Long creditAccountId, Long sourceDepositAccountId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        CreditAccount creditAccount = creditAccountRepository.findById(creditAccountId)
                .orElseThrow(() -> new RuntimeException("Credit account not found"));

        entityManager.lock(creditAccount, LockModeType.PESSIMISTIC_WRITE);

        if (creditAccount.getStatus() == CreditAccountStatus.CLOSED) {
            throw new IllegalStateException("Cannot make payment to a closed credit account");
        }

        BigDecimal outstanding = creditAccount.getOutstandingBalance() == null ? BigDecimal.ZERO : creditAccount.getOutstandingBalance();
        BigDecimal maxAllowed = outstanding.add(outstanding.multiply(new BigDecimal("0.05")));
        if (amount.compareTo(maxAllowed) > 0) {
            throw new IllegalArgumentException("Payment amount exceeds outstanding balance plus 5% overpayment tolerance. Outstanding: " + outstanding);
        }

        Account sourceAccount = accountRepository.findById(sourceDepositAccountId)
                .orElseThrow(() -> new RuntimeException("Source deposit account not found"));

        Transaction paymentTransaction = new Transaction();
        paymentTransaction.setTransactionType(TransactionType.PAYMENT);
        paymentTransaction.setChannel(TransactionChannel.CARD);
        paymentTransaction.setAmount(amount);
        paymentTransaction.setRemarks("Credit card payment - CreditAccount: " + creditAccountId);
        paymentTransaction.setTransactionId(utils.generateReference());
        paymentTransaction.setReferenceNo(utils.generateReference());

        String creditControlAccount = "credit-control-" + creditAccountId;

        transactionPostingService.debit(paymentTransaction, sourceAccount.getAccountNumber(), amount);

        paymentTransaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(paymentTransaction);

        BigDecimal newOutstanding = outstanding.subtract(amount);
        if (newOutstanding.compareTo(BigDecimal.ZERO) < 0) {
            newOutstanding = BigDecimal.ZERO;
        }
        creditAccount.setOutstandingBalance(newOutstanding);

        if (creditAccount.getStatementBalance() != null && creditAccount.getPaymentDueDate() != null) {
            if (LocalDate.now().isBefore(creditAccount.getPaymentDueDate()) || LocalDate.now().isEqual(creditAccount.getPaymentDueDate())) {
                if (newOutstanding.compareTo(BigDecimal.ZERO) == 0) {
                    creditAccount.setInGracePeriod(true);
                } else if (creditAccount.getStatementBalance() != null && newOutstanding.compareTo(creditAccount.getStatementBalance().subtract(amount)) <= 0) {
                    creditAccount.setInGracePeriod(true);
                }
            }
        }

        creditAccountRepository.save(creditAccount);

        log.info("Credit payment made: creditAccount={}, sourceAccount={}, amount={}, newOutstanding={}, isInGracePeriod={}",
                creditAccountId, sourceAccount.getAccountNumber(), amount, creditAccount.getOutstandingBalance(), creditAccount.isInGracePeriod());

        return creditAccount;
    }
}
