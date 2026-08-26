package com.elitetech_inc.ensarkbank.standing_order.repository;

import com.elitetech_inc.ensarkbank.common.enums.StandingOrderStatus;
import com.elitetech_inc.ensarkbank.standing_order.entity.StandingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StandingOrderRepository extends JpaRepository<StandingOrder, Long> {

    List<StandingOrder> findBySourceAccountId(Long accountId);

    List<StandingOrder> findByStatus(StandingOrderStatus status);

    @Query("SELECT so FROM StandingOrder so WHERE so.status = 'ACTIVE' AND so.nextExecutionDate <= :date")
    List<StandingOrder> findDueOrders(@Param("date") LocalDate date);

    boolean existsByIdAndSourceAccountHoldersCustomerId(Long id, Long customerId);
}
