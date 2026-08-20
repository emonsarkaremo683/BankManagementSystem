package com.elitetech_inc.ensarkbank.standing_order.dto;

import com.elitetech_inc.ensarkbank.common.enums.StandingOrderFrequency;
import com.elitetech_inc.ensarkbank.common.enums.StandingOrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StandingOrderResponse {
    private Long id;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private String targetAccountName;
    private BigDecimal amount;
    private StandingOrderFrequency frequency;
    private StandingOrderStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextExecutionDate;
    private LocalDate lastExecutionDate;
    private int executionCount;
    private int maxExecutions;
    private String description;
}
