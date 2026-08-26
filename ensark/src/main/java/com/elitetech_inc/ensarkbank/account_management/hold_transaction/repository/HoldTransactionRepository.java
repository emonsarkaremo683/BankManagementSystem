package com.elitetech_inc.ensarkbank.account_management.hold_transaction.repository;

import com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity.HoldTransaction;
import com.elitetech_inc.ensarkbank.common.enums.HoldReason;
import com.elitetech_inc.ensarkbank.common.enums.HoldStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoldTransactionRepository extends JpaRepository<HoldTransaction, Long> {

    List<HoldTransaction> findByAccountIdAndStatus(Long accountId, HoldStatus status);

    List<HoldTransaction> findByStatusAndExpiresAtBefore(HoldStatus status, LocalDateTime expiresAt);

    Optional<HoldTransaction> findByAuthorizationReferenceAndStatus(String authorizationReference, HoldStatus status);

    List<HoldTransaction> findByAccountIdAndStatusAndReason(Long accountId, HoldStatus status, HoldReason reason);

    @Query("SELECT COALESCE(SUM(h.amount), 0) FROM HoldTransaction h WHERE h.account.id = :accountId AND h.status = 'ACTIVE'")
    BigDecimal sumActiveHoldsByAccountId(@Param("accountId") Long accountId);

    boolean existsByAccountIdAndStatusAndReason(Long accountId, HoldStatus status, HoldReason reason);

    @Query("SELECT COUNT(h) > 0 FROM HoldTransaction h WHERE h.account.id = :accountId AND h.status = 'ACTIVE' AND h.reason <> 'PENDING_APPROVAL'")
    boolean hasActiveCardHolds(@Param("accountId") Long accountId);

    List<HoldTransaction> findByCreditAccountIdAndStatus(Long creditAccountId, HoldStatus status);

    @Query("SELECT COALESCE(SUM(h.amount), 0) FROM HoldTransaction h WHERE h.creditAccount.id = :creditAccountId AND h.status = 'ACTIVE'")
    BigDecimal sumActiveHoldsByCreditAccountId(@Param("creditAccountId") Long creditAccountId);

    @Query("SELECT COUNT(h) > 0 FROM HoldTransaction h WHERE h.creditAccount.id = :creditAccountId AND h.status = 'ACTIVE'")
    boolean hasActiveHoldsByCreditAccountId(@Param("creditAccountId") Long creditAccountId);
}
