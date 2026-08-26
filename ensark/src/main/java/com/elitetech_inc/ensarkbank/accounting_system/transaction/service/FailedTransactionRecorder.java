package com.elitetech_inc.ensarkbank.accounting_system.transaction.service;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persists a FAILED transaction status outside the caller's transaction.
 *
 * <p>{@code TransactionServiceImpl.processTransaction} runs inside a single
 * {@code @Transactional} boundary. Once a posting step throws, that
 * transaction is marked rollback-only, so simply calling
 * {@code transactionRepository.save(transaction)} from the catch block and
 * then rethrowing does not persist anything — the save is rolled back along
 * with everything else, and the FAILED status is lost.</p>
 *
 * <p>This service commits the FAILED status in its own
 * {@code REQUIRES_NEW} transaction, so it survives the rollback of the
 * transaction that triggered it.</p>
 */
@Service
@RequiredArgsConstructor
public class FailedTransactionRecorder {

    private final TransactionRepository transactionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailure(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
