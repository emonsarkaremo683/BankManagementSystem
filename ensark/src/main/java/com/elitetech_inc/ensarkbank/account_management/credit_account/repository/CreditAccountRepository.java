package com.elitetech_inc.ensarkbank.account_management.credit_account.repository;

import com.elitetech_inc.ensarkbank.account_management.credit_account.entity.CreditAccount;
import com.elitetech_inc.ensarkbank.common.enums.CreditAccountStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditAccountRepository extends JpaRepository<CreditAccount, Long> {

    Optional<CreditAccount> findByCardId(Long cardId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ca FROM CreditAccount ca WHERE ca.id = :id")
    Optional<CreditAccount> findByIdForUpdate(@Param("id") Long id);

    Optional<CreditAccount> findByCustomerId(Long customerId);

    List<CreditAccount> findByStatus(CreditAccountStatus status);

    @Query("SELECT ca FROM CreditAccount ca WHERE ca.status = 'ACTIVE' AND ca.billingCycleDay = :dayOfMonth")
    List<CreditAccount> findActiveByBillingCycleDay(@Param("dayOfMonth") int dayOfMonth);

    @Query("SELECT COUNT(ca) > 0 FROM CreditAccount ca WHERE ca.customer.id = :customerId AND ca.id = :creditAccountId")
    boolean existsByCustomerIdAndCreditAccountId(@Param("customerId") Long customerId, @Param("creditAccountId") Long creditAccountId);
}
