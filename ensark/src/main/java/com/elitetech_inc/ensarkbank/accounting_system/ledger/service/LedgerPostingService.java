package com.elitetech_inc.ensarkbank.accounting_system.ledger.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity.HoldTransaction;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.service.HoldTransactionService;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.common.enums.BalanceEffect;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * The ONLY service authorized to modify account balances.
 *
 * <p>This service is the single source of truth for all balance mutations.
 * No other service may directly modify {@link Account#availableBalance},
 * {@link Account#currentBalance}, or {@link Account#holdBalance}.</p>
 *
 * <p>Balance effects are calculated by the {@link AccountingRuleEngine}
 * based on the account's {@link AccountCategory} and the {@link EntryType},
 * ensuring correct accounting treatment for all account types.</p>
 *
 * <h3>Design Principles:</h3>
 * <ul>
 *   <li>Single Responsibility — only modifies balances</li>
 *   <li>Open/Closed — new account categories can be added without changing this service</li>
 *   <li>Dependency Inversion — depends on AccountingRuleEngine abstraction</li>
 *   <li>All mutations go through {@link #applyEntry} for consistency</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Lazy
@Transactional
public class LedgerPostingService {

    private final AccountRepository accountRepository;
    private final AccountingRuleEngine accountingRuleEngine;
    private final AccountValidationService validationService;
    // HoldTransactionServiceImpl injects this service via ObjectProvider to avoid
    // a construction cycle, so we do the same here: a deferred-lookup handle
    // rather than a hard constructor dependency back into HoldTransactionService.
    private final ObjectProvider<HoldTransactionService> holdTransactionServiceProvider;

    /**
     * Applies a single journal entry to an account's balance.
     *
     * <p>This is the core method. It determines the balance effect using
     * the {@link AccountingRuleEngine}, then updates the appropriate
     * balance fields on the account.</p>
     *
     * <p><b>Concurrency / retry:</b> {@link Account} carries a JPA
     * {@code @Version} column, so two concurrent postings to the same
     * account race on an optimistic lock. This method deliberately does
     * <em>not</em> retry the conflict itself: retrying here (or worse,
     * retrying via {@code REQUIRES_NEW}) would either operate on a Hibernate
     * session that the spec says must be discarded after a failed flush, or
     * would let one leg of a debit/credit pair commit independently of the
     * other and break double-entry atomicity. Instead, the
     * {@link org.springframework.orm.ObjectOptimisticLockingFailureException}
     * that Spring's exception translation produces from the eventual flush
     * propagates up to the top-level {@code @Transactional} entry point
     * (e.g. {@code TransactionServiceImpl.createTransaction}), which is
     * annotated {@code @Retryable} and re-runs the whole unit of work —
     * including a fresh re-read of every account — in a brand-new
     * transaction. If retries are exhausted, {@code GlobalExceptionHandler}
     * maps the exception to a 409 response.</p>
     *
     * @param account      the Account entity to update
     * @param entryType    DEBIT or CREDIT
     * @param amount       the amount (must be positive)
     */
    public void applyEntry(Account account, EntryType entryType, BigDecimal amount) {
        if (account == null) {
            throw new IllegalArgumentException("Account is required for ledger posting");
        }
        validationService.validateAmount(amount);

        AccountCategory category = account.getCategory();
        if (category == null) {
            throw new IllegalArgumentException(
                    "Account " + account.getAccountNumber() + " has no category assigned");
        }

        BalanceEffect effect = accountingRuleEngine.resolve(entryType, category);

        BigDecimal currentBalance = zeroIfNull(account.getCurrentBalance());
        BigDecimal availableBalance = zeroIfNull(account.getAvailableBalance());

        account.setCurrentBalance(
                accountingRuleEngine.calculateNewBalance(currentBalance, entryType, category, amount));
        account.setAvailableBalance(
                accountingRuleEngine.calculateNewBalance(availableBalance, entryType, category, amount));

        accountRepository.save(account);

        log.debug("Ledger posting: account={} category={} entry={} amount={} effect={}",
                account.getAccountNumber(), category, entryType, amount, effect);
    }

    /**
     * Applies a journal entry to an account identified by account number.
     * Resolves the account entity and delegates to {@link #applyEntry}.
     *
     * @param accountNumber the account number
     * @param entryType     DEBIT or CREDIT
     * @param amount        the amount
     * @param checkBalance  whether to validate sufficient balance before posting
     */
    public void applyEntry(String accountNumber, EntryType entryType, BigDecimal amount, boolean checkBalance) {
        validationService.validateAmount(amount);
        Account account = validationService.validateAccount(accountNumber);

        if (checkBalance) {
            validationService.validateSufficientBalance(account, entryType, amount);
        }

        applyEntry(account, entryType, amount);
    }

    /**
     * Creates a hold on an account's available balance.
     *
     * <p>Moves funds from availableBalance to holdBalance. This is
     * used for authorization holds (card POS), cheque holds, etc.</p>
     *
     * @param account  the account to hold funds on
     * @param amount   the hold amount
     * @return the created HoldTransaction
     */
    public HoldTransaction holdAmount(Account account, BigDecimal amount) {
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valid account and positive amount required for hold");
        }

        BigDecimal availableBalance = zeroIfNull(account.getAvailableBalance());
        if (availableBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient available balance for hold. Available: " + availableBalance
                            + ", Hold amount: " + amount);
        }

        account.setAvailableBalance(availableBalance.subtract(amount));
        account.setHoldBalance(zeroIfNull(account.getHoldBalance()).add(amount));

        accountRepository.save(account);
        log.debug("Hold applied: account={} amount={}", account.getAccountNumber(), amount);

        return holdTransactionServiceProvider.getObject().createHold(
                account, amount, com.elitetech_inc.ensarkbank.common.enums.HoldReason.LEGAL_LIEN,
                0, null, "Legacy hold");
    }

    /**
     * Creates a hold with full tracking via HoldTransaction.
     *
     * @param account              the account to hold funds on
     * @param amount               the hold amount
     * @param reason               the hold reason
     * @param holdDurationMinutes  how long the hold lasts
     * @param merchantInfo         merchant information
     * @return the created HoldTransaction
     */
    public HoldTransaction holdAmount(Account account, BigDecimal amount,
                                      com.elitetech_inc.ensarkbank.common.enums.HoldReason reason,
                                      int holdDurationMinutes, String merchantInfo) {
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valid account and positive amount required for hold");
        }

        BigDecimal availableBalance = zeroIfNull(account.getAvailableBalance());
        if (availableBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Insufficient available balance for hold. Available: " + availableBalance
                            + ", Hold amount: " + amount);
        }

        account.setAvailableBalance(availableBalance.subtract(amount));
        account.setHoldBalance(zeroIfNull(account.getHoldBalance()).add(amount));
        accountRepository.save(account);

        HoldTransaction hold = holdTransactionServiceProvider.getObject().createHold(
                account, amount, reason, holdDurationMinutes, null, merchantInfo);

        log.debug("Hold created: account={} amount={} reason={}", account.getAccountNumber(), amount, reason);
        return hold;
    }

    /**
     * Releases a hold back to available balance.
     *
     * @param accountNumber the account number
     * @param amount        the amount to release
     */
    public void releaseHold(String accountNumber, BigDecimal amount) {
        if (accountNumber == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valid account number and positive amount required for hold release");
        }

        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

        BigDecimal holdBalance = zeroIfNull(account.getHoldBalance());
        if (holdBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Cannot release more than held. Hold: " + holdBalance + ", Release: " + amount);
        }

        account.setAvailableBalance(zeroIfNull(account.getAvailableBalance()).add(amount));
        account.setHoldBalance(holdBalance.subtract(amount));

        accountRepository.save(account);
        log.debug("Hold released: account={} amount={}", accountNumber, amount);
    }

    /**
     * Releases a hold tracked by a HoldTransaction record.
     *
     * @param hold the HoldTransaction to release
     */
    public void releaseHold(HoldTransaction hold) {
        holdTransactionServiceProvider.getObject().releaseHold(hold);
    }

    /**
     * Moves a held amount back to available balance for the given account,
     * without touching the owning {@link HoldTransaction}'s status.
     *
     * <p>This is the balance-mutation half of releasing a hold. Callers that
     * need to record a specific terminal status (RELEASED, EXPIRED, ...) on
     * the HoldTransaction remain responsible for that bookkeeping; this
     * method only exists so that every path that moves money out of
     * {@code holdBalance} back into {@code availableBalance} goes through
     * this service, per the class-level contract above.</p>
     *
     * @param account the account the hold was placed against
     * @param amount  the amount that was held
     */
    public void returnHoldFunds(Account account, BigDecimal amount) {
        if (account == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valid account and positive amount required to return hold funds");
        }

        BigDecimal holdBalance = zeroIfNull(account.getHoldBalance());
        if (holdBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    "Cannot release more than held. Hold: " + holdBalance + ", Release: " + amount);
        }

        account.setAvailableBalance(zeroIfNull(account.getAvailableBalance()).add(amount));
        account.setHoldBalance(holdBalance.subtract(amount));

        accountRepository.save(account);
        log.debug("Hold funds returned: account={} amount={}", account.getAccountNumber(), amount);
    }

    private BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
