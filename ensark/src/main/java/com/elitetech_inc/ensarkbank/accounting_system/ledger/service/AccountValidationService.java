package com.elitetech_inc.ensarkbank.accounting_system.ledger.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.BalanceEffect;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Centralized pre-posting validation service.
 *
 * <p>All validations that must pass BEFORE a journal entry is posted
 * are consolidated here. This follows the Single Responsibility Principle
 * and ensures consistent validation across all posting paths.</p>
 *
 * <h3>Validations performed:</h3>
 * <ul>
 *   <li>Positive amount</li>
 *   <li>Account exists</li>
 *   <li>Account is active</li>
 *   <li>Account is not frozen</li>
 *   <li>Account is not closed</li>
 *   <li>Sufficient balance (for debits on accounts where debit decreases balance)</li>
 *   <li>Account has a valid category</li>
 * </ul>
 *
 * <p>Note: these are pre-posting checks against whatever balance the caller's
 * transaction has read so far. They cannot themselves detect an optimistic-lock
 * conflict (that only surfaces at flush/commit) — concurrent-update retry is
 * handled at the top-level {@code @Transactional} entry point instead. See
 * {@link LedgerPostingService#applyEntry(Account, EntryType, BigDecimal)}.</p>
 */
@Service
@RequiredArgsConstructor
public class AccountValidationService {

    private final AccountRepository accountRepository;
    private final AccountingRuleEngine accountingRuleEngine;

    /**
     * Validates that an amount is positive.
     */
    public void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive, got: " + amount);
        }
    }

    /**
     * Validates that an account exists and is in a valid state for posting.
     *
     * @param accountNumber the account number to validate
     * @return the validated Account entity
     */
    public Account validateAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number is required");
        }

        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account not found: " + accountNumber));

        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            throw new IllegalArgumentException(
                    "Account is closed: " + accountNumber);
        }

        if (account.getAccountStatus() == AccountStatus.FREEZE) {
            throw new IllegalArgumentException(
                    "Account is frozen: " + accountNumber);
        }

        if (account.getAccountStatus() == AccountStatus.BLOCKED) {
            throw new IllegalArgumentException(
                    "Account is blocked: " + accountNumber);
        }

        if (account.getCategory() == null) {
            throw new IllegalArgumentException(
                    "Account has no category assigned: " + accountNumber);
        }

        return account;
    }

    /**
     * Validates that an account has sufficient balance for a debit entry
     * that would decrease its balance.
     *
     * <p>For example, debiting a customer deposit (LIABILITY) decreases
     * the balance, so sufficient funds must exist. Debiting an expense
     * account increases the balance, so no sufficiency check is needed.</p>
     *
     * @param account   the account to check
     * @param entryType DEBIT or CREDIT
     * @param amount    the amount to post
     */
    public void validateSufficientBalance(Account account, EntryType entryType, BigDecimal amount) {
        BalanceEffect effect = accountingRuleEngine.resolve(entryType, account.getCategory());

        if (effect == BalanceEffect.DECREASE) {
            BigDecimal availableBalance = account.getAvailableBalance() != null
                    ? account.getAvailableBalance()
                    : BigDecimal.ZERO;

            if (availableBalance.compareTo(amount) < 0) {
                throw new IllegalArgumentException(
                        "Insufficient balance for " + entryType + " on account "
                                + account.getAccountNumber()
                                + ". Available: " + availableBalance
                                + ", Required: " + amount);
            }
        }
    }

    /**
     * Performs all validations for a journal entry posting.
     *
     * @param accountNumber the account number
     * @param entryType     DEBIT or CREDIT
     * @param amount        the amount
     * @param checkBalance  whether to check sufficient balance (false for items like loan disbursement)
     * @return the validated Account entity
     */
    public Account validateAll(String accountNumber, EntryType entryType, BigDecimal amount, boolean checkBalance) {
        validateAmount(amount);
        Account account = validateAccount(accountNumber);

        if (checkBalance) {
            validateSufficientBalance(account, entryType, amount);
        }

        return account;
    }
}
