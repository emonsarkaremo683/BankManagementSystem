package com.elitetech_inc.ensarkbank.standing_order.mapper;

import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderRequest;
import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderResponse;
import com.elitetech_inc.ensarkbank.standing_order.entity.StandingOrder;
import org.springframework.stereotype.Component;

@Component
public class StandingOrderMapper {

    public StandingOrderResponse toResponse(StandingOrder entity) {
        if (entity == null) {
            return null;
        }

        StandingOrderResponse response = new StandingOrderResponse();
        response.setId(entity.getId());
        response.setSourceAccountNumber(entity.getSourceAccount() != null ? entity.getSourceAccount().getAccountNumber() : null);
        response.setTargetAccountNumber(entity.getTargetAccountNumber());
        response.setTargetAccountName(entity.getTargetAccountName());
        response.setAmount(entity.getAmount());
        response.setFrequency(entity.getFrequency());
        response.setStatus(entity.getStatus());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setNextExecutionDate(entity.getNextExecutionDate());
        response.setLastExecutionDate(entity.getLastExecutionDate());
        response.setExecutionCount(entity.getExecutionCount());
        response.setMaxExecutions(entity.getMaxExecutions());
        response.setDescription(entity.getDescription());
        return response;
    }

    public StandingOrder toEntity(StandingOrderRequest request) {
        if (request == null) {
            return null;
        }

        StandingOrder entity = new StandingOrder();
        entity.setTargetAccountNumber(request.getTargetAccountNumber());
        entity.setTargetAccountName(request.getTargetAccountName());
        entity.setAmount(request.getAmount());
        entity.setFrequency(request.getFrequency());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setMaxExecutions(request.getMaxExecutions());
        entity.setDescription(request.getDescription());
        return entity;
    }
}