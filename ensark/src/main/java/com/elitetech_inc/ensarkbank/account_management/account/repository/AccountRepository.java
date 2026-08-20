package com.elitetech_inc.ensarkbank.account_management.account.repository;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findAccountByBranchId(Long branchId);
    List<Account> findAllByBranchId(Long branchId);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
    boolean existsById(Long id);

    List<Account> findByAccountTypeInAndAccountStatus(List<AccountType> accountTypes, AccountStatus status);

    List<Account> findDistinctByHoldersCustomerId(Long customerId);

    @Query("SELECT DISTINCT a.accountNumber FROM Account a JOIN a.holders h WHERE h.customer.id = :customerId")
    List<String> findDistinctAccountNumbersByHoldersCustomerId(@Param("customerId") Long customerId);

//    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a JOIN a.holders h where h.customer.id = :customerId")
//    BigDecimal sumBalanceByHoldersCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(a) > 0 FROM Account a JOIN a.holders h WHERE a.id = :accountId AND h.customer.id = :customerId")
    boolean existsByAccountIdAndCustomerId(@Param("accountId") Long accountId, @Param("customerId") Long customerId);

    @Query("SELECT COUNT(a) > 0 FROM Account a JOIN a.holders h WHERE a.accountNumber = :accountNumber AND h.customer.id = :customerId")
    boolean existsByAccountNumberAndCustomerId(@Param("accountNumber") String accountNumber, @Param("customerId") Long customerId);

    long countByBranchIdIn(List<Long> branchIds);

    long countByBranchId(Long branchId);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.branch.id = :branchId")
    long countByBranchIdDirect(@Param("branchId") Long branchId);

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.branch.id = :branchId")
    BigDecimal sumBalanceByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.branch.id IN :branchIds")
    BigDecimal sumBalanceByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a where a.accountNumber != 'Owners Equity'")
    BigDecimal sumBalanceAll();

    @Query("SELECT a.branch.id, a.branch.name, COUNT(a), COUNT(DISTINCT ah.customer.id), COALESCE(SUM(a.availableBalance), 0) " +
           "FROM Account a LEFT JOIN a.holders ah " +
           "WHERE a.branch.type <> com.elitetech_inc.ensarkbank.common.enums.BranchType.HEAD_OFFICE " +
           "GROUP BY a.branch.id, a.branch.name")
    List<Object[]> getBranchWiseSummary();

    @Query("SELECT COUNT(DISTINCT ah.customer.id) FROM Account a LEFT JOIN a.holders ah WHERE a.branch.id IN :branchIds")
    long countDistinctCustomersByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COUNT(DISTINCT ah.customer.id) FROM Account a LEFT JOIN a.holders ah")
    long countDistinctCustomersAll();

    @Query("SELECT a.accountType, COUNT(a) FROM Account a WHERE a.branch.id IN :branchIds GROUP BY a.accountType")
    List<Object[]> countByAccountTypeGrouped(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT a.accountType, COUNT(a) FROM Account a GROUP BY a.accountType")
    List<Object[]> countByAccountTypeGroupedAll();

    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountNumber like 'acc%' AND a.createdAt BETWEEN :start AND :end")
    long countByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.branch.id IN :branchIds AND a.createdAt BETWEEN :start AND :end")
    long countByBranchIdInAndCreatedAtBetween(@Param("branchIds") List<Long> branchIds, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT ah.customer.id) FROM Account a LEFT JOIN a.holders ah WHERE a.createdAt BETWEEN :start AND :end")
    long countDistinctCustomersByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT ah.customer.id) FROM Account a LEFT JOIN a.holders ah WHERE a.branch.id IN :branchIds AND a.createdAt BETWEEN :start AND :end")
    long countDistinctCustomersByBranchIdsAndCreatedAtBetween(@Param("branchIds") List<Long> branchIds, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.category = com.elitetech_inc.ensarkbank.common.enums.AccountCategory.ASSET")
    BigDecimal sumAssetBalanceAll();

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.category = com.elitetech_inc.ensarkbank.common.enums.AccountCategory.LIABILITY")
    BigDecimal sumLiabilityBalanceAll();

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.category = com.elitetech_inc.ensarkbank.common.enums.AccountCategory.ASSET AND a.branch.id IN :branchIds")
    BigDecimal sumAssetBalanceByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.category = com.elitetech_inc.ensarkbank.common.enums.AccountCategory.LIABILITY AND a.branch.id IN :branchIds")
    BigDecimal sumLiabilityBalanceByBranchIds(@Param("branchIds") List<Long> branchIds);

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.category = com.elitetech_inc.ensarkbank.common.enums.AccountCategory.INCOME")
    BigDecimal sumIncomeBalanceAll();

    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM Account a WHERE a.category = com.elitetech_inc.ensarkbank.common.enums.AccountCategory.EXPENSE")
    BigDecimal sumExpenseBalanceAll();

    @Query("SELECT DISTINCT a FROM Account a JOIN a.holders h WHERE h.customer.user.email = :email")
    List<Account> findByCustomerEmail(@Param("email") String email);

    @Query("SELECT a FROM Account a WHERE a.accountType = com.elitetech_inc.ensarkbank.common.enums.AccountType.BRANCH_VAULT")
    List<Account> findAllBranchVaults();

    @Query("SELECT a FROM Account a WHERE a.accountType = com.elitetech_inc.ensarkbank.common.enums.AccountType.BRANCH_VAULT AND a.branch.id = :branchId")
    Optional<Account> findBranchVaultByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT a FROM Account a WHERE " +
            "LOWER(a.accountNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.accountType) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Account> search(@Param("query") String query);

    @Query("SELECT a FROM Account a WHERE a.accountStatus = :status AND (:branchIds IS NULL OR a.branch.id IN :branchIds)")
    List<Account> findByStatusAndBranchIn(@Param("status") AccountStatus status, @Param("branchIds") List<Long> branchIds);
}
