package com.elitetech_inc.ensarkbank.report_management.ledger.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.accounting_system.ledger.service.AccountingRuleEngine;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.common.enums.BalanceEffect;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportLineResponse;
import com.elitetech_inc.ensarkbank.report_management.ledger.dto.response.LedgerReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerReportServiceImpl implements LedgerReportService {

    private final JournalRepository journalRepository;
    private final AccountRepository accountRepository;
    private final AccountingRuleEngine accountingRuleEngine;
    private final com.elitetech_inc.ensarkbank.report_management.ReportExportService reportExportService;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    @Override
    @Transactional(readOnly = true)
    public List<LedgerReportResponse> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> buildLedger(account.getAccountNumber(), null, null))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LedgerReportResponse> findByBranchId(Long branchId) {
        List<Account> accounts = accountRepository.findAllByBranchId(branchId);
        return accounts.stream()
                .map(account -> buildLedger(account.getAccountNumber(), null, null))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LedgerReportResponse> findByBranchIdAndTimeSpan(Long branchId, LocalDate from, LocalDate to) {
        List<Account> accounts = accountRepository.findAllByBranchId(branchId);
        return accounts.stream()
                .map(account -> buildLedger(account.getAccountNumber(), from, to))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LedgerReportResponse> getAllByTimeSpan(LocalDate from, LocalDate to) {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> buildLedger(account.getAccountNumber(), from, to))
                .toList();
    }

    private LedgerReportResponse buildLedger(String accountNumber, LocalDate fromDate, LocalDate toDate) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElse(null);

        if (account == null) {
            return null;
        }

        List<Journal> journals = filterByDate(journalRepository.getJournalsByAccountNumber(accountNumber), fromDate, toDate);
        journals.sort(Comparator.comparing(j -> j.getCreatedAt() == null ? LocalDateTime.MIN : j.getCreatedAt()));

        BigDecimal openingBalance = getOpeningBalance(account, fromDate);

        BigDecimal balance = openingBalance;
        for (Journal j : journals) {
            balance = applyEntry(balance, j, account.getCategory());
        }

        LedgerReportResponse response = new LedgerReportResponse();
        response.setBranchId(account.getBranch() != null ? account.getBranch().getId() : null);
        response.setBranchName(account.getBranch() != null ? account.getBranch().getName() : "Head Office");
        response.setAccountNumber(accountNumber);
        response.setOpeningBalance(openingBalance);
        response.setClosingBalance(balance);

        List<LedgerReportLineResponse> lines = new ArrayList<>();
        BigDecimal running = openingBalance;

        if (journals.isEmpty()) {
            BigDecimal currentBal = account.getCurrentBalance() != null ? account.getCurrentBalance() : ZERO;
            LedgerReportLineResponse initLine = new LedgerReportLineResponse();
            initLine.setJournalId(account.getId());
            initLine.setDate(account.getCreatedAt() != null ? account.getCreatedAt() : LocalDateTime.now());
            initLine.setTransactionId("INIT-ACC-" + account.getId());
            initLine.setParticulars("Opening Account Balance Posting");
            initLine.setAccountNumber(accountNumber);
            String custName1 = !account.getHolders().isEmpty() && account.getHolders().getFirst().getCustomer() != null ? account.getHolders().getFirst().getCustomer().getName() : null;
            initLine.setAccountName(custName1 != null ? custName1 : "Account " + accountNumber);
            initLine.setDebit(currentBal);
            initLine.setCredit(ZERO);
            initLine.setBalance(currentBal);
            lines.add(initLine);
            response.setClosingBalance(currentBal);
        } else {
            for (Journal j : journals) {
                running = applyEntry(running, j, account.getCategory());
                LedgerReportLineResponse line = new LedgerReportLineResponse();
                line.setJournalId(j.getId());
                line.setDate(j.getCreatedAt());
                line.setTransactionId(j.getTransaction() != null ? j.getTransaction().getTransactionId() : "TXN-" + j.getId());
                line.setParticulars(j.getEntryType() == EntryType.DEBIT ? "Debit Posting" : "Credit Posting");
                line.setAccountNumber(j.getAccountNumber());
                String custName2 = !account.getHolders().isEmpty() && account.getHolders().getFirst().getCustomer() != null ? account.getHolders().getFirst().getCustomer().getName() : null;
                line.setAccountName(custName2 != null ? custName2 : (account.getBranch() != null ? account.getBranch().getName() : accountNumber));
                line.setDebit(j.getEntryType() == EntryType.DEBIT ? j.getAmount() : ZERO);
                line.setCredit(j.getEntryType() == EntryType.CREDIT ? j.getAmount() : ZERO);
                line.setBalance(running);
                lines.add(line);
            }
        }
        response.setEntries(lines);
        return response;
    }

    private BigDecimal applyEntry(BigDecimal balance, Journal j, AccountCategory category) {
        if (category == null) {
            category = AccountCategory.LIABILITY;
        }
        BalanceEffect effect = accountingRuleEngine.resolve(j.getEntryType(), category);
        return switch (effect) {
            case INCREASE -> balance.add(j.getAmount());
            case DECREASE -> balance.subtract(j.getAmount());
        };
    }

    private List<Journal> filterByDate(List<Journal> journals, LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null && toDate == null) {
            return journals;
        }
        LocalDateTime from = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime to = toDate != null ? toDate.atTime(23, 59, 59) : null;
        return new ArrayList<>(journals.stream().filter(j -> {
            LocalDateTime d = j.getCreatedAt();
            if (d == null) return false;
            if (from != null && d.isBefore(from)) return false;
            return to == null || !d.isAfter(to);
        }).toList());
    }

    private BigDecimal getOpeningBalance(Account acc, LocalDate fromDate) {
        List<String> an = List.of(acc.getAccountNumber());

        LocalDateTime startDate = acc.getCreatedAt() != null ? acc.getCreatedAt() : LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime endDate;
        if (fromDate != null) {
            endDate = fromDate.atStartOfDay().minusNanos(1);
        } else {
            endDate = LocalDateTime.now();
        }

        List<Journal> list = journalRepository.findTransactionHistory(an, startDate, endDate).stream().toList();

        BigDecimal totalDebit = list.stream()
                .filter(j -> j.getEntryType() == EntryType.DEBIT)
                .map(Journal::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCredit = list.stream()
                .filter(j -> j.getEntryType() == EntryType.CREDIT)
                .map(Journal::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (acc.getCategory() == null) {
            return BigDecimal.ZERO;
        }
        return switch (acc.getCategory()) {
            case ASSET, EXPENSE -> totalDebit.subtract(totalCredit);
            case LIABILITY, EQUITY, INCOME -> totalCredit.subtract(totalDebit);
        };
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToPdf(Long branchId, LocalDate from, LocalDate to) {
        List<LedgerReportResponse> data = (branchId == null)
                ? getAllByTimeSpan(from, to)
                : findByBranchIdAndTimeSpan(branchId, from, to);
        return reportExportService.exportLedgerToPdf(data);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToExcel(Long branchId, LocalDate from, LocalDate to) {
        List<LedgerReportResponse> data = (branchId == null)
                ? getAllByTimeSpan(from, to)
                : findByBranchIdAndTimeSpan(branchId, from, to);
        return reportExportService.exportLedgerToExcel(data);
    }
}
