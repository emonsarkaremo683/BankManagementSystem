package com.elitetech_inc.ensarkbank.accounting_system.journal.dto;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.repository.AccountTransactionRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class JournalMapper {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final JournalRepository journalRepository;

    public JournalResponse toResponse(Journal journal) {
        Transaction transaction = journal.getTransaction();
        String transactionId = transaction.getTransactionId();
        String counterpartyAccountNumber = getAccountNumber(journal.getEntryType(), transactionId);

        JournalResponse jr = new JournalResponse();
        jr.setId(journal.getId());
        jr.setTransactionEntityId(transaction.getId());
        jr.setDate(journal.getCreatedAt());
        jr.setTransactionId(transactionId);
        jr.setParticulars(getParticulars(journal));
        jr.setAmount(journal.getAmount());
        jr.setAccountNumber(journal.getAccountNumber());
        jr.setCounterpartyAccountNumber(counterpartyAccountNumber);
        jr.setCounterpartyName(getName(counterpartyAccountNumber, transaction.getId()));
        jr.setEntryType(journal.getEntryType());
        jr.setTransactionType(transaction.getTransactionType());
        jr.setChannel(transaction.getChannel());
        jr.setStatus(transaction.getStatus());
        jr.setRemarks(transaction.getRemarks());

        return jr;
    }


    private String getParticulars(Journal journal) {
        Transaction transaction = journal.getTransaction();
        EntryType entryType = journal.getEntryType();
        String transactionId = transaction.getTransactionId();

        switch (transaction.getTransactionType()) {

            case DEPOSIT:
                return "Cash Deposit";

            case WITHDRAW:
                return "Cash Withdrawal";

            case ATM_DEPOSIT:
                return "ATM Deposit";

            case ATM_WITHDRAW:
                return "ATM Withdrawal";

            case TRANSFER: {
                String counterpartyAccountNumber = getAccountNumber(entryType, transactionId);
                String counterpartyName = getName(counterpartyAccountNumber, transaction.getId());
                return entryType == EntryType.DEBIT
                        ? "Transfer to " + counterpartyName
                        : "Transfer from " + counterpartyName;
            }

            case PAYMENT: {
                String counterpartyAccountNumber = getAccountNumber(entryType, transactionId);
                String counterpartyName = getName(counterpartyAccountNumber, transaction.getId());
                return "Payment - " + counterpartyName;
            }

            case REFUND: {
                String counterpartyAccountNumber = getAccountNumber(entryType, transactionId);
                String counterpartyName = getName(counterpartyAccountNumber, transaction.getId());
                return "Refund - " + counterpartyName;
            }

            case LOAN_DISBURSEMENT:
                return "Loan Disbursement";

            case LOAN_REPAYMENT:
                return "Loan Repayment";

            case REVERSE: {
                String counterpartyAccountNumber = getAccountNumber(entryType, transactionId);
                String counterpartyName = getName(counterpartyAccountNumber, transaction.getId());
                return "Reverse - " + counterpartyName;
            }

            case CARD_PURCHASE:
                return "Card Purchase";

            case CARD_REVERSAL:
                return "Card Reversal";

            case BRANCH_OPENING:
                return "Branch Opening";

            default:
                return transaction.getTransactionType().name();
        }
    }


    private String getName(String accountNumber, Long id) {

        return accountRepository.findAccountByAccountNumber(accountNumber)
                .map(account -> {

                    if (account.getAccountType() == AccountType.BRANCH_VAULT) {
                        return account.getBranch().getName();
                    }

                    if (account.getHolders() == null || account.getHolders().isEmpty()) {
                        return account.getAccountNumber();
                    }

                    var firstHolder = account.getHolders().getFirst();
                    return firstHolder.getCustomer() != null ? firstHolder.getCustomer().getName() : account.getAccountNumber();
                })
                .orElseGet(() -> accountTransactionRepository.findAccountTransactionByTransactionId(id)
                        .map(AccountTransaction::getReceiverName)
                        .orElse("")
                );
    }

    private String getAccountNumber(EntryType et, String transactionId) {
        Transaction t = transactionRepository.findByTransactionId(transactionId).orElseThrow(
                ()-> new RuntimeException("transaction id not found")
        );

        List<Journal> entries = t.getEntries();
        if (entries == null || entries.isEmpty()) {
            return null;
        }

        return entries.stream()
                .filter(d -> d.getEntryType() != et)
                .map(Journal::getAccountNumber)
                .findFirst()                         // Finds the first match
                .orElse(null);

    }


}
