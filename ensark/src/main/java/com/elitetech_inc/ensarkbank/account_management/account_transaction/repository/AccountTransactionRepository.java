package com.elitetech_inc.ensarkbank.account_management.account_transaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    @Query("""
        select at from AccountTransaction at
        join at.account ac
        where ac.accountNumber = :accountNumber
""")
    List<AccountTransaction> findByAccountTransactionByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("""
        select at from AccountTransaction at
        where at.receiverAccountNumber = :accountNumber
""")
    List<AccountTransaction> findByReceiverAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("""
        select at from AccountTransaction at
        join at.account ac
        where ac.id = :accountId
        order by at.createdAt desc
""")
    List<AccountTransaction> findByAccountId(@Param("accountId") Long accountId);

    Optional<AccountTransaction> findAccountTransactionByTransactionId(Long transactionId);

    @Query("""
        SELECT COUNT(t) > 0 FROM AccountTransaction t
        LEFT JOIN t.account a LEFT JOIN a.holders sh
        LEFT JOIN t.receiver r LEFT JOIN r.holders rh
        WHERE t.id = :transactionId
        AND (sh.customer.id = :customerId OR rh.customer.id = :customerId)
    """)
    boolean existsByTransactionIdAndCustomerId(@Param("transactionId") Long transactionId, @Param("customerId") Long customerId);

    @Query("SELECT DISTINCT at FROM AccountTransaction at JOIN at.account a WHERE a.branch.id IN :branchIds ORDER BY at.createdAt DESC")
    List<AccountTransaction> findByBranchIds(@Param("branchIds") List<Long> branchIds);
}
