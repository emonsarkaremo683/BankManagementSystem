package com.elitetech_inc.ensarkbank.accounting_system.transaction.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.AccountTransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.common.enums.FraudRiskLevel;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.common.exception.TooManyRequestsException;
import com.elitetech_inc.ensarkbank.common.notification.websocket.DashboardFeedService;
import com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag;
import com.elitetech_inc.ensarkbank.fraud_detection.service.FraudDetectionService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.mapper.TransactionMapper;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import com.elitetech_inc.ensarkbank.util.BranchValidator;
import com.elitetech_inc.ensarkbank.util.RequestValidator;
import com.elitetech_inc.ensarkbank.util.Utils;

import com.elitetech_inc.ensarkbank.account_management.card.repository.CardRepository;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import java.math.RoundingMode;
import com.elitetech_inc.ensarkbank.common.enums.Currency;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final Utils utils;
    private final TransactionPostingService transactionPostingService;
    private final AccountRepository accountRepository;
    private final BranchRepository branchRepository;
    private final BranchValidator branchValidator;
    private final RequestValidator requestValidator;
    private final JournalRepository journalRepository;
    private final DashboardFeedService dashboardFeedService;
    private final CardRepository cardRepository;
    // ObjectProvider, not a @Lazy field: AccountServiceImpl calls back into
    // this service (sweep/deposit postings), so a direct dependency here
    // would form a construction cycle. A field-level @Lazy on a
    // Lombok-generated constructor parameter does not reliably suppress
    // Spring's circular-reference check (confirmed at runtime), so this uses
    // a deferred-lookup handle instead.
    private final ObjectProvider<AccountService> accountServiceProvider;
    private final FailedTransactionRecorder failedTransactionRecorder;
    private final FraudDetectionService fraudDetectionService;
    /**
     * Self-injected proxy, used only so that the {@code @Retryable} entry
     * points below invoke the {@code @Transactional} work through the Spring
     * proxy (see {@link #createTransaction} and {@link #reverseTransaction}).
     * A plain {@code this.createTransactionInternal(...)} call would bypass
     * both the retry and transaction advice entirely, since Spring's AOP
     * advice only applies at proxy boundaries, not to internal method calls.
     * ObjectProvider, not a @Lazy field, for the same reason as above — a
     * self-reference is also a cycle, and @Lazy alone didn't suppress it.
     */
    private final ObjectProvider<TransactionServiceImpl> selfProvider;


    /**
     * Public entry point. Not itself {@code @Transactional}: it exists so
     * {@code @Retryable} can retry the whole unit of work — including a
     * fresh re-read of every account — in a brand-new transaction each
     * attempt when a concurrent update loses the optimistic-lock race on
     * {@link com.elitetech_inc.ensarkbank.account_management.account.entity.Account}.
     * Putting {@code @Transactional} on this same method would make the
     * ordering of the retry and transaction advice unspecified, and could
     * retry inside an already-doomed transaction instead of starting a new
     * one — so the transactional work is delegated to
     * {@link #createTransactionInternal} through the self-proxy.
     */
    @Override
    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 150, multiplier = 2, maxDelay = 1500)
    )
    public TransactionResponse createTransaction(TransactionRequest tr, Transaction t,
                                                 String sender,
                                                 String receiverAccount) {
        return selfProvider.getObject().createTransactionInternal(tr, t, sender, receiverAccount);
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public TransactionResponse createTransactionInternal(TransactionRequest tr, Transaction t,
                                                 String sender,
                                                 String receiverAccount) {
        if (tr == null) {
            throw new IllegalArgumentException("Transaction request is required");
        }
        requestValidator.validateTransactionRequest(tr);

        Account senderAccount = accountRepository.findAccountByAccountNumber(sender)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found"));

        if (senderAccount.getBranch() != null) {
            branchValidator.assertNotAgentBank(senderAccount.getBranch().getId());
        }


        return processTransaction(tr, t, senderAccount, receiverAccount);
    }

    /**
     * See {@link #createTransaction} for why the retry/transaction split exists.
     */
    @Override
    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 150, multiplier = 2, maxDelay = 1500)
    )
    public TransactionResponse reverseTransaction(Long transactionId) {
        return selfProvider.getObject().reverseTransactionInternal(transactionId);
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public TransactionResponse reverseTransactionInternal(Long transactionId){
        Transaction original = transactionRepository.findById(transactionId).orElseThrow(
                ()-> new ResourceNotFoundException("Not found " + transactionId)
        );

        if (original.getStatus() == TransactionStatus.REVERSED) {
            throw new IllegalStateException("Transaction " + transactionId + " has already been reversed");
        }

        String creditAccount = journalRepository.findJournalByTransaction_Id(transactionId)
                .stream()
                .filter(journal -> journal.getEntryType() == EntryType.CREDIT)
                .map(Journal::getAccountNumber)
                .findFirst()
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Not Found")
                );

        String debitAccount = journalRepository.findJournalByTransaction_Id(transactionId)
                .stream()
                .filter(journal -> journal.getEntryType() == EntryType.DEBIT)
                .map(Journal::getAccountNumber)
                .findFirst()
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Not Found")
                );

        original.setStatus(TransactionStatus.REVERSED);
        transactionRepository.save(original);

        Transaction reversal = new Transaction();
        reversal.setTransactionId(utils.generateReference());
        reversal.setReferenceNo(utils.generateReference());
        reversal.setAmount(original.getAmount());
        reversal.setTransactionType(TransactionType.REVERSE);
        reversal.setChannel(original.getChannel());
        reversal.setRemarks("Reversal of transaction #" + transactionId);
        reversal.setStatus(TransactionStatus.SUCCESS);
        reversal.setChargeAmount(BigDecimal.ZERO);
        reversal.setVatAmount(BigDecimal.ZERO);

        transactionPostingService.reverseDebit(reversal, debitAccount, reversal.getAmount());
        transactionPostingService.reverseCredit(reversal, creditAccount, reversal.getAmount());

        return transactionMapper.toResponse(transactionRepository.save(reversal));
    }

    @Override
    public TransactionResponse loanRepayment(TransactionRequest request, Account customerAccount, Account loanControlAccount, BigDecimal interest, BigDecimal principle) {
        return null;
    }

    @Override
    public Optional<Transaction> findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }


    private TransactionResponse processTransaction(TransactionRequest tr, Transaction t,
                                                   Account senderAccount,
                                                   String receiver) {
        if (tr == null || tr.getAmount() == null) {
            throw new IllegalArgumentException("Transaction amount is required");
        }
        if (tr.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }

        Account receiverAccount = null;
        if(accountRepository.existsByAccountNumber(receiver)){
            receiverAccount = resolveReceiver(receiver);
        }

        Transaction transaction = t != null ? t : new Transaction();
        transaction.setAmount(tr.getAmount());
        transaction.setRemarks(tr.getRemarks());
        transaction.setTransactionType(t != null && t.getTransactionType() != null ? t.getTransactionType() : TransactionType.TRANSFER);
        transaction.setChannel(t != null && t.getChannel() != null ? t.getChannel() : TransactionChannel.INTERNET_BANKING);
        transaction.setTransactionId(utils.generateReference());
        transaction.setReferenceNo(utils.generateReference());
        if (transaction.getCurrency() == null) {
            transaction.setCurrency(senderAccount != null && senderAccount.getCurrency() != null ? senderAccount.getCurrency() : Currency.BDT);
        }

        BigDecimal charge = BigDecimal.ZERO;
        if (transaction.getTransactionType() == TransactionType.TRANSFER && senderAccount != null) {
            boolean isInterbank = (receiverAccount == null);
            if (isInterbank) {
                boolean isForeign = senderAccount.getCurrency() != Currency.BDT || transaction.getCurrency() != Currency.BDT;
                if (isForeign) {
                    boolean hasMultiCurrencyCard = cardRepository.findByAccountAccountNumber(senderAccount.getAccountNumber())
                            .stream()
                            .anyMatch(card -> card.isInternationalEnabled() && card.getStatus() == CardStatus.ACTIVE);
                    if (!hasMultiCurrencyCard) {
                        throw new BadRequestException("Foreign transfer is blocked: sender account does not have an active multi-currency card.");
                    }
                    charge = transaction.getAmount().multiply(new BigDecimal("0.05")).setScale(4, RoundingMode.HALF_UP);
                } else {
                    charge = transaction.getAmount().multiply(new BigDecimal("0.02")).setScale(4, RoundingMode.HALF_UP);
                }
            }
        }

        if (senderAccount != null) {
            BigDecimal totalDebit = transaction.getAmount().add(charge);
            accountServiceProvider.getObject().validateMinimumBalance(senderAccount.getAccountNumber(), totalDebit);
        }

        transaction.setChargeAmount(charge);
        transaction.setVatAmount(transaction.getVatAmount() == null ? BigDecimal.ZERO : transaction.getVatAmount());

        try {
            checkForFraud(senderAccount, transaction.getAmount());

            switch (transaction.getTransactionType()) {
                case TRANSFER:
                    if (senderAccount == null) {
                        throw new IllegalArgumentException("Sender accounts are required for transfer");
                    }

                    transactionPostingService.transfer(transaction,
                            senderAccount.getAccountNumber(),
                            receiverAccount != null ? receiverAccount.getAccountNumber() : receiver,
                            transaction.getAmount());

                    if (charge.compareTo(BigDecimal.ZERO) > 0) {
                        Account feeAccount = accountServiceProvider.getObject().getOrCreateFeeIncomeAccount(senderAccount.getBranch());
                        transactionPostingService.feeCharge(transaction,
                                senderAccount.getAccountNumber(),
                                feeAccount.getAccountNumber(),
                                charge);
                    }
                    break;
                case DEPOSIT:
                    transactionPostingService.cashDeposit(transaction,
                            senderAccount.getAccountNumber(),
                            receiverAccount != null ? receiverAccount.getAccountNumber() : receiver,
                            transaction.getAmount());
                    break;
                case WITHDRAW:
                    transactionPostingService.cashWithdrawal(transaction,
                            senderAccount.getAccountNumber(),
                            receiverAccount != null ? receiverAccount.getAccountNumber() : receiver,
                            transaction.getAmount());
                    break;

                case LOAN_DISBURSEMENT:
                    transactionPostingService.loanDisbursement(transaction,
                            senderAccount.getAccountNumber(),
                            receiverAccount != null ? receiverAccount.getAccountNumber() : receiver,
                            transaction.getAmount());
                    break;
                case LOAN_REPAYMENT:
                    transactionPostingService.loanRepayment(transaction,
                            senderAccount.getAccountNumber(),
                            receiverAccount != null ? receiverAccount.getAccountNumber() : receiver,
                            transaction.getAmount());
                    break;
                case LOAN_FORECLOSURE:
                    transactionPostingService.loanForeclosure(transaction,
                            senderAccount.getAccountNumber(),
                            receiverAccount != null ? receiverAccount.getAccountNumber() : receiver,
                            transaction.getAmount());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getTransactionType());
            }

            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (RuntimeException ex) {
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setRemarks(
                    (transaction.getRemarks() == null ? "" : transaction.getRemarks() + " | ") + ex.getMessage()
            );
            // The enclosing @Transactional is about to roll back because of this
            // exception, which would also undo a plain save() here. Persist the
            // FAILED status in its own REQUIRES_NEW transaction so it survives.
            failedTransactionRecorder.recordFailure(transaction);
            throw ex;
        }

        Transaction saved = transactionRepository.save(transaction);

        if (transaction.getStatus() == TransactionStatus.SUCCESS) {
            Account refreshedSender = accountRepository.findAccountByAccountNumber(senderAccount.getAccountNumber()).orElse(senderAccount);
            boolean isCredit = false;
            List<Long> senderUserIds = extractUserIds(refreshedSender);
            dashboardFeedService.pushTransactionUpdate(senderUserIds, transaction.getId(),
                    transaction.getReferenceNo(),
                    transaction.getTransactionType() != null ? transaction.getTransactionType().name() : null,
                    transaction.getAmount(), isCredit, refreshedSender.getAvailableBalance(),
                    refreshedSender.getAccountNumber(), transaction.getRemarks(),
                    transaction.getChannel() != null ? transaction.getChannel().name() : null);

            if (receiverAccount != null) {
                Account refreshedReceiver = accountRepository.findAccountByAccountNumber(receiverAccount.getAccountNumber()).orElse(receiverAccount);
                List<Long> receiverUserIds = extractUserIds(refreshedReceiver);
                dashboardFeedService.pushTransactionUpdate(receiverUserIds, transaction.getId(),
                        transaction.getReferenceNo(),
                        transaction.getTransactionType() != null ? transaction.getTransactionType().name() : null,
                        transaction.getAmount(), true, refreshedReceiver.getAvailableBalance(),
                        refreshedReceiver.getAccountNumber(), transaction.getRemarks(),
                        transaction.getChannel() != null ? transaction.getChannel().name() : null);
            }
        }

        return transactionMapper.toResponse(saved);
    }


    private Account resolveReceiver(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return null;
        }

        if (accountRepository.existsByAccountNumber(accountNumber)) {
            return accountRepository.findAccountByAccountNumber(accountNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));
        }

        return null;
    }

    private List<Long> extractUserIds(Account account) {
        if (account == null || account.getHolders() == null) {
            return List.of();
        }
        return account.getHolders().stream()
                .filter(h -> h.getCustomer() != null && h.getCustomer().getUser() != null)
                .map(h -> h.getCustomer().getUser().getId())
                .toList();
    }

    /**
     * Wires FraudDetectionService.checkTransaction into the posting flow,
     * before any ledger entries are written. A HIGH/CRITICAL flag blocks the
     * transaction outright (thrown as a 429, caught by the same
     * FAILED-status handling as any other posting failure); MEDIUM/LOW is
     * recorded for review but does not block the transfer. checkTransaction
     * itself no-ops (returns null) when nothing looks suspicious.
     */
    private void checkForFraud(Account senderAccount, BigDecimal amount) {
        if (senderAccount == null) {
            return;
        }
        List<Long> senderUserIds = extractUserIds(senderAccount);
        Long userId = senderUserIds.isEmpty() ? null : senderUserIds.get(0);

        FraudFlag flag = fraudDetectionService.checkTransaction(
                userId, senderAccount.getId(), null, amount, utils.getClientIp(), utils.getDeviceInfo());

        if (flag != null && (flag.getRiskLevel() == FraudRiskLevel.HIGH || flag.getRiskLevel() == FraudRiskLevel.CRITICAL)) {
            throw new TooManyRequestsException(
                    "Transaction blocked: elevated fraud risk detected on account " + senderAccount.getAccountNumber()
                            + ". Please contact support.");
        }
    }
}
