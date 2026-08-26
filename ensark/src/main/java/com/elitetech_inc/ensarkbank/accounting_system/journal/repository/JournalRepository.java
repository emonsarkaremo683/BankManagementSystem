package com.elitetech_inc.ensarkbank.accounting_system.journal.repository;

import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> getJournalsByAccountNumber(String accountNumber);

    @Query("SELECT j FROM Journal j WHERE j.account.branch.id = :branchId")
    List<Journal> findByBranchId(@Param("branchId") Long branchId);

    List<Journal> findJournalByTransaction_Id(Long transactionId);

    @Query("SELECT j FROM Journal j WHERE j.account.branch.id = :branchId " +
            "AND j.createdAt >= :from AND j.createdAt <= :to")
    List<Journal> findByBranchIdAndDateRange(@Param("branchId") Long branchId,
                                             @Param("from") LocalDateTime from,
                                             @Param("to") LocalDateTime to);

    @Query("SELECT j FROM Journal j WHERE j.createdAt >= :from AND j.createdAt <= :to")
    List<Journal> findByDateRange(@Param("from") LocalDateTime from,
                                  @Param("to") LocalDateTime to);

    @Query("""
        SELECT j
        FROM Journal j
        WHERE j.accountNumber IN :accountNumbers
          AND j.createdAt BETWEEN :startDate AND :endDate
        ORDER BY j.createdAt DESC
        """)
    List<Journal> findTransactionHistory(
            @Param("accountNumbers") List<String> accountNumbers,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT j FROM Journal j WHERE j.accountNumber IN :accountNumbers")
    List<Journal> findTransactionHistory(@Param("accountNumbers") List<String> accountNumbers);

    @Query("SELECT j FROM Journal j ORDER BY j.createdAt DESC")
    List<Journal> findAllJournals();

    @Query("SELECT j FROM Journal j WHERE j.accountNumber = :accountNumber ORDER BY j.createdAt DESC")
    List<Journal> findByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT j FROM Journal j " +
            "JOIN j.account a " +
            "JOIN a.holders h " +
            "JOIN h.customer c " +
            "JOIN c.user u " +
            "WHERE u.email = :email " +
            "ORDER BY j.createdAt DESC")
    List<Journal> findByAccountHoldersCustomerUserEmail(@Param("email") String email);

    @Query("SELECT j FROM Journal j " +
            "JOIN j.account a " +
            "JOIN a.holders h " +
            "JOIN h.customer c " +
            "JOIN c.user u " +
            "WHERE u.email = :email " +
            "AND j.createdAt >= :from AND j.createdAt <= :to " +
            "ORDER BY j.createdAt DESC")
    List<Journal> findByAccountHoldersCustomerUserEmailAndDateRange(
            @Param("email") String email,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query("SELECT j FROM Journal j " +
            "WHERE j.accountNumber = :accountNumber " +
            "AND j.createdAt >= :from AND j.createdAt <= :to " +
            "ORDER BY j.createdAt DESC")
    List<Journal> findByAccountNumberAndDateRange(
            @Param("accountNumber") String accountNumber,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    @Query("SELECT j FROM Journal j " +
            "WHERE LOWER(j.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY j.createdAt DESC")
    List<Journal> search(@Param("query") String query);
}
