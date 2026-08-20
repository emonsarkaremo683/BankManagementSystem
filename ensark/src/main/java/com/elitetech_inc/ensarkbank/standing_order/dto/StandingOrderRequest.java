package com.elitetech_inc.ensarkbank.standing_order.dto;

import com.elitetech_inc.ensarkbank.common.enums.StandingOrderFrequency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StandingOrderRequest {
    private Long sourceAccountId;
    private String targetAccountNumber;
    private String targetAccountName;
    private BigDecimal amount;
    private StandingOrderFrequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxExecutions;
    private String description;
}
