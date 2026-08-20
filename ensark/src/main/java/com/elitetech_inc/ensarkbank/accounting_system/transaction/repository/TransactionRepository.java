package com.elitetech_inc.ensarkbank.accounting_system.transaction.repository;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {



    Optional<Transaction> findByTransactionId(String transactionId);

    List<Transaction> findByRemarksContaining(String remarks);

    @Query("SELECT COUNT(t) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds")
    long countByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT t.transactionType, COUNT(t) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds GROUP BY t.transactionType")
    List<Object[]> countByTransactionTypeGrouped(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT t.transactionType, COUNT(t) FROM Transaction t JOIN t.entries j JOIN j.account a GROUP BY t.transactionType")
    List<Object[]> countByTransactionTypeGroupedAll();

    @Query("SELECT t.status, COUNT(t) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds GROUP BY t.status")
    List<Object[]> countByStatusGrouped(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT t.status, COUNT(t) FROM Transaction t JOIN t.entries j JOIN j.account a GROUP BY t.status")
    List<Object[]> countByStatusGroupedAll();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds")
    BigDecimal sumAmountByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT FUNCTION('DATE', t.createdAt), COUNT(t), COALESCE(SUM(t.amount), 0) " +
           "FROM Transaction t JOIN t.entries j JOIN j.account a " +
           "WHERE t.createdAt BETWEEN :start AND :end AND a.branch.id IN :branchIds " +
           "GROUP BY FUNCTION('DATE', t.createdAt)")
    List<Object[]> sumAmountByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("branchIds") List<Long> branchIds);

    @Query("SELECT FUNCTION('DATE', t.createdAt), COUNT(t), COALESCE(SUM(t.amount), 0) " +
           "FROM Transaction t " +
           "WHERE t.createdAt BETWEEN :start AND :end " +
           "GROUP BY FUNCTION('DATE', t.createdAt)")
    List<Object[]> sumAmountByDateRangeAll(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(t) FROM Transaction t")
    long countAll();

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.createdAt BETWEEN :start AND :end")
    long countByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(t) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds AND t.createdAt BETWEEN :start AND :end")
    long countByBranchIdsAndCreatedAtBetween(@Param("branchIds") List<Long> branchIds, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT t.transactionType, COALESCE(SUM(t.amount), 0) FROM Transaction t GROUP BY t.transactionType")
    List<Object[]> sumAmountByTypeGroupedAll();

    @Query("SELECT t.transactionType, COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds GROUP BY t.transactionType")
    List<Object[]> sumAmountByTypeGrouped(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT FUNCTION('DATE', t.createdAt), " +
           "COALESCE(SUM(CASE WHEN t.transactionType IN ('DEPOSIT','ATM_DEPOSIT','LOAN_DISBURSEMENT','LOAN_REPAYMENT','INTEREST_POSTING','REFUND') THEN t.amount ELSE 0 END), 0), " +
           "COALESCE(SUM(CASE WHEN t.transactionType IN ('WITHDRAW','ATM_WITHDRAW','TRANSFER','PAYMENT','CARD_PURCHASE') THEN t.amount ELSE 0 END), 0) " +
           "FROM Transaction t " +
           "WHERE t.createdAt BETWEEN :start AND :end " +
           "GROUP BY FUNCTION('DATE', t.createdAt)")
    List<Object[]> sumInflowOutflowByDateAll(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT FUNCTION('DATE', t.createdAt), " +
           "COALESCE(SUM(CASE WHEN t.transactionType IN ('DEPOSIT','ATM_DEPOSIT','LOAN_DISBURSEMENT','LOAN_REPAYMENT','INTEREST_POSTING','REFUND') THEN t.amount ELSE 0 END), 0), " +
           "COALESCE(SUM(CASE WHEN t.transactionType IN ('WITHDRAW','ATM_WITHDRAW','TRANSFER','PAYMENT','CARD_PURCHASE') THEN t.amount ELSE 0 END), 0) " +
           "FROM Transaction t JOIN t.entries j JOIN j.account a " +
           "WHERE t.createdAt BETWEEN :start AND :end AND a.branch.id IN :branchIds " +
           "GROUP BY FUNCTION('DATE', t.createdAt)")
    List<Object[]> sumInflowOutflowByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.transactionType IN ('DEPOSIT','ATM_DEPOSIT')")
    BigDecimal sumDepositAll();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE t.transactionType IN ('DEPOSIT','ATM_DEPOSIT') AND a.branch.id IN :branchIds")
    BigDecimal sumDepositByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.transactionType IN ('WITHDRAW','ATM_WITHDRAW')")
    BigDecimal sumWithdrawAll();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE t.transactionType IN ('WITHDRAW','ATM_WITHDRAW') AND a.branch.id IN :branchIds")
    BigDecimal sumWithdrawByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.transactionType = 'TRANSFER'")
    BigDecimal sumTransferAll();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE t.transactionType = 'TRANSFER' AND a.branch.id IN :branchIds")
    BigDecimal sumTransferByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType IN ('DEPOSIT','ATM_DEPOSIT','LOAN_DISBURSEMENT','LOAN_REPAYMENT','INTEREST_POSTING','REFUND') THEN t.amount ELSE 0 END), 0) FROM Transaction t")
    BigDecimal sumCashInflowAll();

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType IN ('DEPOSIT','ATM_DEPOSIT','LOAN_DISBURSEMENT','LOAN_REPAYMENT','INTEREST_POSTING','REFUND') THEN t.amount ELSE 0 END), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds")
    BigDecimal sumCashInflowByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType IN ('WITHDRAW','ATM_WITHDRAW','TRANSFER','PAYMENT','CARD_PURCHASE') THEN t.amount ELSE 0 END), 0) FROM Transaction t")
    BigDecimal sumCashOutflowAll();

    @Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType IN ('WITHDRAW','ATM_WITHDRAW','TRANSFER','PAYMENT','CARD_PURCHASE') THEN t.amount ELSE 0 END), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE a.branch.id IN :branchIds")
    BigDecimal sumCashOutflowByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.transactionType IN ('INTEREST_POSTING','PAYMENT') AND t.status = 'COMPLETED'")
    BigDecimal sumRevenueAll();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.entries j JOIN j.account a WHERE t.transactionType IN ('INTEREST_POSTING','PAYMENT') AND t.status = 'COMPLETED' AND a.branch.id IN :branchIds")
    BigDecimal sumRevenueByBranchIds(@Param("branchIds") List<Long> branchIds);
}
