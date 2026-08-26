package com.elitetech_inc.ensarkbank.standing_order.service;

import com.elitetech_inc.ensarkbank.common.enums.StandingOrderStatus;
import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderRequest;
import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderResponse;
import com.elitetech_inc.ensarkbank.standing_order.entity.StandingOrder;

import java.util.List;
import java.util.Optional;

public interface StandingOrderService {
    StandingOrderResponse createStandingOrder(StandingOrderRequest standingOrder);
    StandingOrderResponse cancelStandingOrder(Long id);
    StandingOrderResponse pauseStandingOrder(Long id);
    StandingOrderResponse resumeStandingOrder(Long id);
    Optional<StandingOrderResponse> getStandingOrder(Long id);
    List<StandingOrderResponse> getStandingOrdersByAccountId(Long accountId);
    List<StandingOrderResponse> getActiveOrders();
    void processDueOrders();
    StandingOrderResponse updateStandingOrder(Long id, StandingOrderRequest request);
    List<com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse> getExecutionHistory(Long standingOrderId);
}
