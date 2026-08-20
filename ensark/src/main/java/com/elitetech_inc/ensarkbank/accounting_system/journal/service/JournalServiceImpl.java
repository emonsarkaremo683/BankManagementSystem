package com.elitetech_inc.ensarkbank.accounting_system.journal.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalMapper;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalRequest;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import com.elitetech_inc.ensarkbank.accounting_system.journal.repository.JournalRepository;
import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {

    private final JournalRepository journalRepository;
    private final JournalMapper journalMapper;
    private final AccountRepository accountRepository;
    private final TransactionHistoryExportService transactionHistoryExportService;

    @Override
    @Transactional
    public JournalResponse create(JournalRequest request) {
        Account account = accountRepository.findAccountByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", request.getAccountNumber()));

        Journal journal = new Journal();
        journal.setAccount(account);
        journal.setAccountNumber(request.getAccountNumber());
        journal.setEntryType(request.getEntryType());
        journal.setAmount(request.getAmount());

        Journal saved = journalRepository.save(journal);
        return journalMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> findByCustomerEmail(String email) {
        List<Journal> journals = journalRepository.findByAccountHoldersCustomerUserEmail(email);
        return journals.stream()
                .filter(j -> j.getAccountNumber().startsWith("acc-"))
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> findByAccountNumber(String accountNumber) {
        return journalRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> findByCustomerEmailAndTimeSpan(String email, LocalDateTime from, LocalDateTime to) {
        List<Journal> journals = journalRepository.findByAccountHoldersCustomerUserEmailAndDateRange(email, from, to);
        return journals.stream()
                .filter(j -> j.getAccountNumber().startsWith("acc-"))
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> findByAccountNumberAndTimeSpan(String accountNumber, LocalDateTime from, LocalDateTime to) {
        return journalRepository.findByAccountNumberAndDateRange(accountNumber, from, to)
                .stream()
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> getAll() {
        return journalRepository.findAllJournals()
                .stream()
                .filter(j -> j.getAccountNumber().startsWith("acc-"))
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> findByBranchId(Long branchId) {
        return journalRepository.findByBranchId(branchId)
                .stream()
                .filter(j -> j.getAccountNumber().startsWith("acc-"))
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> search(String query) {
        return journalRepository.search(query)
                .stream()
                .filter(j -> j.getAccountNumber().startsWith("acc-"))
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JournalResponse> getJournalByCustomerId(Long customerId, LocalDateTime from, LocalDateTime to) {
        List<String> accountNumbers = accountRepository.findDistinctAccountNumbersByHoldersCustomerId(customerId);
        if (accountNumbers.isEmpty()) return List.of();
        return journalRepository.findTransactionHistory(accountNumbers, from, to)
                .stream()
                .map(journalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public JournalResponse findById(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new ResourceNotFoundException("Journal", journalId));
        return journalMapper.toResponse(journal);
    }

    @Override
    @Transactional
    public JournalResponse reverseEntry(Long journalId) {
        Journal original = journalRepository.findById(journalId)
                .orElseThrow(() -> new ResourceNotFoundException("Journal", journalId));

        Journal reverse = new Journal();
        reverse.setAccount(original.getAccount());
        reverse.setAccountNumber(original.getAccountNumber());
        reverse.setEntryType(original.getEntryType() == EntryType.DEBIT ? EntryType.CREDIT : EntryType.DEBIT);
        reverse.setAmount(original.getAmount());
        reverse.setTransaction(original.getTransaction());

        Journal saved = journalRepository.save(reverse);
        return journalMapper.toResponse(saved);
    }

    @Override
    public ByteArrayInputStream exportStatement(String accountNumber, LocalDateTime from, LocalDateTime to, String format) {
        List<JournalResponse> entries = findByAccountNumberAndTimeSpan(accountNumber, from, to);
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber));
        String customerName = account.getHolders().isEmpty() ? "N/A" : account.getHolders().get(0).getCustomer().getName();

        byte[] bytes;
        if ("pdf".equalsIgnoreCase(format)) {
            bytes = transactionHistoryExportService.generatePdf(entries, accountNumber, customerName, from, to);
        } else {
            bytes = transactionHistoryExportService.generateExcel(entries, accountNumber, customerName, from, to);
        }
        return new ByteArrayInputStream(bytes);
    }
}
