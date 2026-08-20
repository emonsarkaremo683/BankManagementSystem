package com.elitetech_inc.ensarkbank.accounting_system.journal.service;

import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalRequest;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

public interface JournalService {
    JournalResponse create(JournalRequest request);
    List<JournalResponse> findByCustomerEmail(String email);
    List<JournalResponse> findByAccountNumber(String accountNumber);
    List<JournalResponse> findByCustomerEmailAndTimeSpan(String email, LocalDateTime from, LocalDateTime to);
    List<JournalResponse> findByAccountNumberAndTimeSpan(String accountNumber, LocalDateTime from, LocalDateTime to);
    List<JournalResponse> getAll();
    List<JournalResponse> findByBranchId(Long branchId);
    List<JournalResponse> search(String query);
    List<JournalResponse> getJournalByCustomerId(Long customerId, LocalDateTime from, LocalDateTime to);
    
    JournalResponse findById(Long journalId);
    JournalResponse reverseEntry(Long journalId);
    ByteArrayInputStream exportStatement(String accountNumber, LocalDateTime from, LocalDateTime to, String format);
}
