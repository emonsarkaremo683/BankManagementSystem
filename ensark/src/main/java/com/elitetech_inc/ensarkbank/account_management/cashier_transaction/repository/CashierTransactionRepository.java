package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.repository;

import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.CashierTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashierTransactionRepository extends JpaRepository<CashierTransaction, Long> {

    @Query("SELECT ct FROM CashierTransaction ct WHERE ct.accountNumber = :accountNumber ORDER BY ct.createdAt DESC")
    List<CashierTransaction> findByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT ct FROM CashierTransaction ct WHERE ct.branch.id = :branchId ORDER BY ct.createdAt DESC")
    List<CashierTransaction> findByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT ct FROM CashierTransaction ct WHERE ct.branch.id IN :branchIds ORDER BY ct.createdAt DESC")
    List<CashierTransaction> findByBranchIds(@Param("branchIds") List<Long> branchIds);


    Optional<CashierTransaction> findCashierTransactionByTransaction_id(Long transactionId);

    Optional<CashierTransaction> findCashierTransactionByCheckNo(String checkNo);
}
