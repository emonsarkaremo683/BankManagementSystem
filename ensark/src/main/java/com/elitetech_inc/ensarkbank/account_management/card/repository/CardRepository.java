package com.elitetech_inc.ensarkbank.account_management.card.repository;

import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

    boolean existsCardByCardNumber(String cardNumber);

    @Query("""
        SELECT c FROM Card c
        JOIN c.account a
        JOIN a.holders h
        WHERE LOWER(c.cardNumber) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(h.customer.name) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(a.accountNumber) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<Card> search(@Param("query") String query);

    List<Card> findByAccountAccountNumber(String accountNumber);

    @Query("""
        SELECT c FROM Card c
        JOIN c.account a
        JOIN a.holders h
        WHERE h.customer.user.email = :email
    """)
    List<Card> findByAccountHoldersCustomerUserEmail(@Param("email") String email);

    Optional<Card> findByAccountId(Long accountId);

    @Query("SELECT COUNT(c) > 0 FROM Card c WHERE c.account.id = :accountId")
    boolean existsByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT COUNT(c) FROM Card c WHERE c.status = :status")
    long countByStatus(@Param("status") CardStatus status);

    @Query("SELECT COUNT(c) FROM Card c JOIN c.account a WHERE c.status = :status AND a.branch.id IN :branchIds")
    long countByStatusAndBranchIds(@Param("status") CardStatus status, @Param("branchIds") List<Long> branchIds);

    @Query("SELECT COUNT(c) FROM Card c JOIN c.account a JOIN a.holders h WHERE h.customer.id = :customerId")
    int countByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(c) > 0 FROM Card c WHERE c.id = :cardId AND c.account.id IN (SELECT ah.account.id FROM AccountHolder ah WHERE ah.customer.id = :customerId)")
    boolean existsByCardIdAndCustomerId(@Param("cardId") Long cardId, @Param("customerId") Long customerId);

    /** Used by the daily reset job (midnight) — see CardLimitResetScheduler. */
    @Modifying
    @Query("UPDATE Card c SET c.currentDailyUsage = 0")
    int resetAllDailyUsage();

    /** Used by the monthly reset job (1st of month) — see CardLimitResetScheduler. */
    @Modifying
    @Query("UPDATE Card c SET c.currentMonthlyUsage = 0")
    int resetAllMonthlyUsage();
}
