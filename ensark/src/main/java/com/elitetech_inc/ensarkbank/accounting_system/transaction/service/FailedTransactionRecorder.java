package com.elitetech_inc.ensarkbank.accounting_system.transaction.service;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class FailedTransactionRecorder {

    private final TransactionRepository transactionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailure(Transaction transaction) {
        if (transaction != null) {
            try {
                transaction.setEntries(null);
                if (transaction.getRemarks() != null && transaction.getRemarks().length() > 1000) {
                    transaction.setRemarks(transaction.getRemarks().substring(0, 1000));
                }
                transactionRepository.save(transaction);
            } catch (Exception e) {
                log.error("Failed to record failed transaction status for ref {}: {}",
                        transaction.getReferenceNo(), e.getMessage());
            }
        }
    }
}
