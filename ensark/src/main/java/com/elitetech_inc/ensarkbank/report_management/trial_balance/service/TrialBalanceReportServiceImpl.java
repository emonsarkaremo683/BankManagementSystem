package com.elitetech_inc.ensarkbank.report_management.trial_balance.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportLineResponse;
import com.elitetech_inc.ensarkbank.report_management.trial_balance.dto.response.TrialBalanceReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrialBalanceReportServiceImpl implements TrialBalanceReportService {

    private final JournalRepository journalRepository;
    private final AccountRepository accountRepository;
    private final com.elitetech_inc.ensarkbank.report_management.ReportExportService reportExportService;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    @Override
    @Transactional(readOnly = true)
    public TrialBalanceReportResponse getAll() {
        List<Journal> journals = journalRepository.findAllJournals();
        return buildTrialBalance(journals, null, "ALL BRANCHES");
    }

    @Override
    @Transactional(readOnly = true)
    public TrialBalanceReportResponse findByBranchId(Long branchId) {
        List<Journal> journals = journalRepository.findByBranchId(branchId);
        String branchName = accountRepository.findAllByBranchId(branchId).stream()
                .findFirst()
                .map(a -> a.getBranch() != null ? a.getBranch().getName() : "Branch " + branchId)
                .orElse("Branch " + branchId);
        return buildTrialBalance(journals, branchId, branchName);
    }

    @Override
    @Transactional(readOnly = true)
    public TrialBalanceReportResponse findByBranchIdAndTimeSpan(Long branchId, LocalDate from, LocalDate to) {
        LocalDateTime fromDate = from.atStartOfDay();
        LocalDateTime toDate = to.atTime(23, 59, 59);
        List<Journal> journals = journalRepository.findByBranchIdAndDateRange(branchId, fromDate, toDate);
        String branchName = accountRepository.findAllByBranchId(branchId).stream()
                .findFirst()
                .map(a -> a.getBranch() != null ? a.getBranch().getName() : "Branch " + branchId)
                .orElse("Branch " + branchId);
        return buildTrialBalance(journals, branchId, branchName);
    }

    @Override
    @Transactional(readOnly = true)
    public TrialBalanceReportResponse getAllByTimeSpan(LocalDate from, LocalDate to) {
        LocalDateTime fromDate = from.atStartOfDay();
        LocalDateTime toDate = to.atTime(23, 59, 59);
        List<Journal> journals = journalRepository.findByDateRange(fromDate, toDate);
        return buildTrialBalance(journals, null, "ALL BRANCHES");
    }

    private TrialBalanceReportResponse buildTrialBalance(List<Journal> journals, Long branchId, String branchName) {
        TrialBalanceReportResponse response = new TrialBalanceReportResponse();
        BigDecimal totalDebit = ZERO;
        BigDecimal totalCredit = ZERO;
        List<TrialBalanceReportLineResponse> lines = new ArrayList<>();

        Map<String, TrialBalanceReportLineResponse> byAccount = new LinkedHashMap<>();
        if (journals.isEmpty()) {
            List<Account> accounts = branchId != null ? accountRepository.findAllByBranchId(branchId) : accountRepository.findAll();
            for (Account acc : accounts) {
                BigDecimal bal = acc.getCurrentBalance() != null ? acc.getCurrentBalance() : (acc.getAvailableBalance() != null ? acc.getAvailableBalance() : ZERO);
                TrialBalanceReportLineResponse l = new TrialBalanceReportLineResponse();
                l.setAccountNumber(acc.getAccountNumber());
                String custName = !acc.getHolders().isEmpty() && acc.getHolders().getFirst().getCustomer() != null ? acc.getHolders().getFirst().getCustomer().getName() : "Account";
                l.setAccountName(custName + " [" + acc.getAccountType() + "]");
                l.setGlCode("GL-ACC-" + acc.getId());
                
                String typeStr = acc.getAccountType() != null ? acc.getAccountType().name() : "";
                if (typeStr.contains("LOAN") || typeStr.contains("MORTGAGE") || typeStr.contains("OVERDRAFT")) {
                    l.setDebit(bal.abs());
                    l.setCredit(ZERO);
                } else {
                    l.setDebit(ZERO);
                    l.setCredit(bal.abs());
                }
                byAccount.put(acc.getAccountNumber(), l);
            }
        } else {
            for (Journal j : journals) {
                Account account = j.getAccount();
                if (account == null) continue;
                String key = account.getAccountNumber();
                TrialBalanceReportLineResponse line = byAccount.computeIfAbsent(key, k -> {
                    TrialBalanceReportLineResponse l = new TrialBalanceReportLineResponse();
                    l.setAccountNumber(k);
                    l.setAccountName(account.getBranch() != null ? account.getBranch().getName() : k);
                    l.setGlCode("ACC-" + account.getId());
                    l.setDebit(ZERO);
                    l.setCredit(ZERO);
                    return l;
                });
                if (j.getEntryType() == EntryType.DEBIT) {
                    line.setDebit(line.getDebit().add(j.getAmount()));
                } else {
                    line.setCredit(line.getCredit().add(j.getAmount()));
                }
            }
        }
        for (TrialBalanceReportLineResponse line : byAccount.values()) {
            totalDebit = totalDebit.add(line.getDebit());
            totalCredit = totalCredit.add(line.getCredit());
            lines.add(line);
        }

        response.setBranchId(branchId);
        response.setBranchName(branchName);
        response.setLines(lines);
        response.setTotalDebit(totalDebit);
        response.setTotalCredit(totalCredit);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToPdf(Long branchId, LocalDate from, LocalDate to) {
        TrialBalanceReportResponse report = (branchId == null)
                ? getAllByTimeSpan(from, to)
                : findByBranchIdAndTimeSpan(branchId, from, to);
        return reportExportService.exportTrialBalanceToPdf(report);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportToExcel(Long branchId, LocalDate from, LocalDate to) {
        TrialBalanceReportResponse report = (branchId == null)
                ? getAllByTimeSpan(from, to)
                : findByBranchIdAndTimeSpan(branchId, from, to);
        return reportExportService.exportTrialBalanceToExcel(report);
    }
}
