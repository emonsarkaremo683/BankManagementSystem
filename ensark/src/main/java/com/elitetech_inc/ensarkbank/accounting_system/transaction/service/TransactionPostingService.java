package com.elitetech_inc.ensarkbank.accounting_system.transaction.service;

import java.math.BigDecimal;
import java.util.List;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity.HoldTransaction;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.Loan;
import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanRepayment;
import com.elitetech_inc.ensarkbank.account_management.loan.repository.LoanRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.ledger.service.AccountValidationService;
import com.elitetech_inc.ensarkbank.accounting_system.ledger.service.LedgerPostingService;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.common.enums.HoldReason;
import com.elitetech_inc.ensarkbank.common.enums.RepaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;

/**
 * Orchestrates double-entry journal posting for all transaction types.
 *
 * <p>This service is responsible for:</p>
 * <ul>
 *   <li>Creating balanced DEBIT/CREDIT journal entries</li>
 *   <li>Delegating balance updates exclusively to {@link LedgerPostingService}</li>
 *   <li>Validating accounts via {@link AccountValidationService}</li>
 * </ul>
 *
 * <p><b>IMPORTANT:</b> This service NEVER directly modifies account balances.
 * All balance mutations go through {@link LedgerPostingService} which uses
 * the {@link com.elitetech_inc.ensarkbank.accounting_system.ledger.service.AccountingRuleEngine}
 * to determine the correct balance effect based on AccountCategory.</p>
 *
 * <h3>Design:</h3>
 * <p>Follows the Facade pattern — provides high-level posting methods
 * (transfer, loanDisbursement, etc.) that compose journal entries and
 * delegate balance updates. Each method creates the appropriate DEBIT
 * and CREDIT entries, then calls LedgerPostingService to apply them.</p>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionPostingService {

    private final AccountRepository accountRepository;
    private final LedgerPostingService ledgerPostingService;
    private final AccountValidationService validationService;
    private final LoanRepository loanRepository;
    // ObjectProvider, not a @Lazy field: AccountServiceImpl calls back into
    // TransactionService (which sits in front of this bean) for sweep/vault
    // postings, so a direct dependency here would form a construction cycle.
    // A field-level @Lazy on a Lombok-generated constructor parameter does
    // not reliably suppress Spring's circular-reference check (confirmed at
    // runtime), so this uses a deferred-lookup handle instead.
    private final ObjectProvider<AccountService> accountServiceProvider;

    /*
     * =====================================================
     * CREDIT POSTING
     * =====================================================
     * Use cases: Deposit, Interest Posting, Refund, Inward Transfer
     */
    public void credit(Transaction transaction, String acc, BigDecimal amount) {
        if (transaction == null || acc == null) {
            throw new IllegalArgumentException("Transaction and account are required");
        }
        BigDecimal normalizedAmount = normalizeAmount(amount);
        Account account = validationService.validateAccount(acc);
        ledgerPostingService.applyEntry(account, EntryType.CREDIT, normalizedAmount);
        addEntry(transaction, acc, EntryType.CREDIT, normalizedAmount);
    }

    /*
     * =====================================================
     * DEBIT POSTING
     * =====================================================
     * Use cases: Withdrawal, ATM, Fee, Penalty, Loan Repayment
     */
    public void debit(Transaction transaction, String acc, BigDecimal amount) {
        if (transaction == null || acc == null) {
            throw new IllegalArgumentException("Transaction and account are required");
        }
        BigDecimal normalizedAmount = normalizeAmount(amount);
        Account account = validationService.validateAll(acc, EntryType.DEBIT, normalizedAmount, true);
        ledgerPostingService.applyEntry(account, EntryType.DEBIT, normalizedAmount);
        addEntry(transaction, acc, EntryType.DEBIT, normalizedAmount);
    }

    /*
     * =====================================================
     * INTERNAL ACCOUNT TRANSFER (same bank)
     * =====================================================
     */
    public void transfer(Transaction transaction, String sender, String receiver, BigDecimal amount) {
        debit(transaction, sender, amount);
        credit(transaction, receiver, amount);
    }

    /*
     * =====================================================
     * OUTWARD BANK TRANSFER (our bank -> other bank)
     * =====================================================
     */
    public void outwardTransfer(Transaction transaction, String customerAccount,
                                String settlementAccount, BigDecimal amount) {
        debit(transaction, customerAccount, amount);
        credit(transaction, settlementAccount, amount);
    }

    /*
     * =====================================================
     * INWARD BANK TRANSFER (other bank -> our bank)
     * =====================================================
     */
    public void inwardTransfer(Transaction transaction, String settlementAccount,
                               String customerAccount, BigDecimal amount) {
        debit(transaction, settlementAccount, amount);
        credit(transaction, customerAccount, amount);
    }

    /*
     * =====================================================
     * ATM CASH DEPOSIT
     * =====================================================
     */
    public void atmCashDeposit(Transaction transaction, String atmCashAccount,
                               String customerAccount, BigDecimal amount) {
        debit(transaction, atmCashAccount, amount);
        credit(transaction, customerAccount, amount);
    }

    /*
     * =====================================================
     * ATM WITHDRAWAL
     * =====================================================
     */
    public void atmWithdrawal(Transaction transaction, String customerAccount,
                              String atmCashAccount, BigDecimal amount) {
        debit(transaction, customerAccount, amount);
        credit(transaction, atmCashAccount, amount);
    }

    /*
     * =====================================================
     * CASH DEPOSIT (BRANCH)
     * =====================================================
     */
    public void cashDeposit(Transaction transaction, String cashVault,
                            String customerAccount, BigDecimal amount) {
        debit(transaction, cashVault, amount);
        credit(transaction, customerAccount, amount);
    }

    /*
     * =====================================================
     * CASH WITHDRAWAL (BRANCH)
     * =====================================================
     */
    public void cashWithdrawal(Transaction transaction, String customerAccount,
                               String cashVault, BigDecimal amount) {
        debit(transaction, customerAccount, amount);
        credit(transaction, cashVault, amount);
    }

    /*
     * =====================================================
     * FEE CHARGE
     * =====================================================
     */
    public void feeCharge(Transaction transaction, String customerAccount,
                          String feeIncomeAccount, BigDecimal amount) {
        debit(transaction, customerAccount, amount);
        credit(transaction, feeIncomeAccount, amount);
    }

    /*
     * =====================================================
     * INTEREST POSTING
     * =====================================================
     */
    public void interestPosting(Transaction transaction, String interestExpenseAccount,
                                String customerAccount, BigDecimal amount) {
        debit(transaction, interestExpenseAccount, amount);
        credit(transaction, customerAccount, amount);
    }

    /*
     * =====================================================
     * LOAN DISBURSEMENT
     * =====================================================
     * The loan control account is debited for the full principal, but the
     * customer only receives principal minus the disbursement charge — the
     * charge itself is credited to the fee income account so debits still
     * equal credits.
     */
    public void loanDisbursement(Transaction transaction, String loanControlAccount,
                                 String customerAccount, BigDecimal amount) {
        BigDecimal normalizedAmount = normalizeAmount(amount);

        Account loanAccount = validationService.validateAccount(loanControlAccount);
        ledgerPostingService.applyEntry(loanAccount, EntryType.DEBIT, normalizedAmount);
        addEntry(transaction, loanControlAccount, EntryType.DEBIT, normalizedAmount);

        Loan loan = loanRepository.findByAccountAccountNumber(customerAccount).orElse(null);
        BigDecimal disbursementCharge = loan != null && loan.getDisbursementCharge() != null
                ? loan.getDisbursementCharge()
                : BigDecimal.ZERO;

        if (disbursementCharge.compareTo(BigDecimal.ZERO) > 0) {
            if (disbursementCharge.compareTo(normalizedAmount) >= 0) {
                throw new IllegalArgumentException(
                        "Disbursement charge (" + disbursementCharge + ") cannot be >= disbursed amount (" + normalizedAmount + ")");
            }
            BigDecimal netToCustomer = normalizedAmount.subtract(disbursementCharge);
            credit(transaction, customerAccount, netToCustomer);

            Account customer = accountRepository.findAccountByAccountNumber(customerAccount)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found: " + customerAccount));
            Account feeIncomeAccount = accountServiceProvider.getObject().getOrCreateFeeIncomeAccount(customer.getBranch());
            credit(transaction, feeIncomeAccount.getAccountNumber(), disbursementCharge);
        } else {
            credit(transaction, customerAccount, normalizedAmount);
        }
    }

    /*
     * =====================================================
     * LOAN REPAYMENT
     * =====================================================
     */
    public void loanRepayment(Transaction transaction, String customerAccount,
                              String loanControlAccount, BigDecimal amount) {

        Account cust = accountRepository.findAccountByAccountNumber(customerAccount)
                .orElseThrow();

        LoanRepayment repayment;
        if (transaction.getLoanRepaymentId() != null) {
            repayment = loanRepository.findByAccountId(cust.getId()).stream()
                    .flatMap(l -> l.getRepayments().stream())
                    .filter(r -> r.getId().equals(transaction.getLoanRepaymentId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Loan repayment not found: " + transaction.getLoanRepaymentId()));
        } else {
            Loan loan = loanRepository.findByAccountId(cust.getId()).stream()
                    .filter(l -> l.getRepayments().stream()
                            .anyMatch(r -> r.getEmiAmount().compareTo(amount) == 0 && r.getStatus() == RepaymentStatus.PENDING))
                    .findFirst()
                    .orElseThrow();

            repayment = loan.getRepayments().stream()
                    .filter(r -> r.getEmiAmount().compareTo(amount) == 0 && r.getStatus() == RepaymentStatus.PENDING)
                    .findFirst()
                    .orElseThrow();
        }

        debit(transaction, customerAccount, amount);

        BigDecimal principal = repayment.getPrincipalComponent();
        BigDecimal interest = repayment.getInterestComponent() != null
                ? repayment.getInterestComponent()
                : BigDecimal.ZERO;

        credit(transaction, loanControlAccount, principal);
        if (interest.compareTo(BigDecimal.ZERO) > 0) {
            credit(transaction, loanControlAccount, interest);
        }

        // LoanServiceImpl charges EMI + a late fee when the installment is
        // overdue (see payInstallmentByAccount/payInstallmentByCashier), so
        // `amount` can exceed principal + interest. That delta is a late fee
        // and must be credited to income — otherwise this debits the
        // customer for more than it credits anywhere, breaking the ledger.
        BigDecimal lateFee = amount.subtract(principal).subtract(interest);
        if (lateFee.compareTo(BigDecimal.ZERO) > 0) {
            Account feeIncomeAccount = accountServiceProvider.getObject().getOrCreateFeeIncomeAccount(cust.getBranch());
            credit(transaction, feeIncomeAccount.getAccountNumber(), lateFee);
        }
    }

    /*
     * =====================================================
     * LOAN FORECLOSURE (early payoff of every remaining installment)
     * =====================================================
     * Unlike loanRepayment(), which settles exactly one scheduled
     * installment (looked up by loanRepaymentId, or as a fallback by
     * matching a single installment's EMI amount), foreclosure settles
     * every PENDING installment on the loan at once. The credit to the loan
     * control account is always the sum of principal + interest across all
     * pending LoanRepayment rows — never a single amount-matched record —
     * so it stays correct no matter how many installments remain.
     */
    public void loanForeclosure(Transaction transaction, String customerAccount,
                                String loanControlAccount, BigDecimal amount) {
        Loan loan = loanRepository.findByAccountAccountNumber(customerAccount)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found for account: " + customerAccount));

        List<LoanRepayment> pending = loan.getRepayments().stream()
                .filter(r -> r.getStatus() == RepaymentStatus.PENDING)
                .toList();

        if (pending.isEmpty()) {
            throw new IllegalArgumentException("No pending installments to foreclose for loan " + loan.getId());
        }

        BigDecimal totalPrincipal = pending.stream()
                .map(LoanRepayment::getPrincipalComponent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalInterest = pending.stream()
                .map(r -> r.getInterestComponent() != null ? r.getInterestComponent() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPayoff = totalPrincipal.add(totalInterest);

        if (totalPayoff.compareTo(amount) != 0) {
            throw new IllegalArgumentException(
                    "Foreclosure amount " + amount + " does not match the sum of pending installments " + totalPayoff);
        }

        debit(transaction, customerAccount, amount);
        if (totalPrincipal.compareTo(BigDecimal.ZERO) > 0) {
            credit(transaction, loanControlAccount, totalPrincipal);
        }
        if (totalInterest.compareTo(BigDecimal.ZERO) > 0) {
            credit(transaction, loanControlAccount, totalInterest);
        }
    }

    /*
     * =====================================================
     * CARD / POS PURCHASE (hold-settle flow)
     * =====================================================
     */
    public void settleCardPurchase(Transaction transaction, String customerAccount,
                                   String merchantSettlementAccount,
                                   BigDecimal heldAmount, BigDecimal settledAmount) {
        releaseHold(customerAccount, heldAmount);
        debit(transaction, customerAccount, settledAmount);
        credit(transaction, merchantSettlementAccount, settledAmount);
    }

    /*
     * =====================================================
     * HOLD BALANCE (legacy)
     * =====================================================
     */
    public void holdAmount(Account account, BigDecimal amount) {
        ledgerPostingService.holdAmount(account, amount);
    }

    /*
     * =====================================================
     * HOLD BALANCE (with HoldTransaction record)
     * =====================================================
     */
    public HoldTransaction holdAmount(Account account, BigDecimal amount,
                                      HoldReason reason, int holdDurationMinutes, String merchantInfo) {
        return ledgerPostingService.holdAmount(account, amount, reason, holdDurationMinutes, merchantInfo);
    }

    /*
     * =====================================================
     * RELEASE HOLD BALANCE (legacy)
     * =====================================================
     */
    public void releaseHold(String acc, BigDecimal amount) {
        ledgerPostingService.releaseHold(acc, amount);
    }

    /*
     * =====================================================
     * RELEASE HOLD BALANCE (by HoldTransaction record)
     * =====================================================
     */
    public void releaseHold(HoldTransaction hold) {
        ledgerPostingService.releaseHold(hold);
    }

    /*
     * =====================================================
     * REVERSAL
     * =====================================================
     */
    public void reverseDebit(Transaction reversalTransaction, String account, BigDecimal amount) {
        credit(reversalTransaction, account, amount);
    }

    public void reverseCredit(Transaction reversalTransaction, String account, BigDecimal amount) {
        debit(reversalTransaction, account, amount);
    }

    /*
     * =====================================================
     * HEAD OFFICE CREATION ENTRY
     * =====================================================
     */
    public void headOfficeCreationEntry(Transaction transaction, String cashAccount,
                                        String capitalAccount, BigDecimal amount) {
        BigDecimal normalizedAmount = normalizeAmount(amount);

        // DEBIT Cash (ASSET) - increases cash balance
        Account cashAcct = validationService.validateAccount(cashAccount);
        debit(transaction, cashAccount, normalizedAmount);

        // CREDIT Capital (EQUITY) - increases equity balance
        Account capitalAcct = validationService.validateAccount(capitalAccount);
        credit(transaction, capitalAccount, normalizedAmount);
    }

    /*
     * =====================================================
     * BRANCH CREATION ENTRY
     * =====================================================
     */
    public void branchCreationEntry(Transaction transaction, String headOfficeAccount,
                                    String branchCapitalAccount, BigDecimal amount) {
        BigDecimal normalizedAmount = normalizeAmount(amount);

        // DEBIT Branch Capital (ASSET) - increases branch balance
        Account branchAccount = validationService.validateAll(
                branchCapitalAccount, EntryType.DEBIT, normalizedAmount, true);
        ledgerPostingService.applyEntry(branchAccount, EntryType.DEBIT, normalizedAmount);
        addEntry(transaction, branchCapitalAccount, EntryType.DEBIT, normalizedAmount);

        // CREDIT Head Office (ASSET) - decreases HO balance
        Account hoAccount = validationService.validateAccount(headOfficeAccount);
        ledgerPostingService.applyEntry(hoAccount, EntryType.CREDIT, normalizedAmount);
        addEntry(transaction, headOfficeAccount, EntryType.CREDIT, normalizedAmount);
    }

    /*
     * =====================================================
     * INTERNAL -- CREATE JOURNAL ENTRY
     * =====================================================
     */
    private void addEntry(Transaction transaction, String accountNumber,
                          EntryType entryType, BigDecimal amount) {
        Journal entry = new Journal();
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElse(null);
        if (account != null) {
            entry.setAccount(account);
        }
        entry.setTransaction(transaction);
        entry.setAccountNumber(accountNumber);
        entry.setEntryType(entryType);
        entry.setAmount(amount);
        transaction.getEntries().add(entry);
    }

    private BigDecimal normalizeAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        return amount;
    }
}
