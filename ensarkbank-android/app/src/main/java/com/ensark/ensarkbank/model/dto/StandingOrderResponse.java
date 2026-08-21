package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.StandingOrderFrequency;
import com.ensark.ensarkbank.model.enums.StandingOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandingOrderResponse {
    private Long id;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private String targetAccountName;
    private BigDecimal amount;
    private StandingOrderFrequency frequency;
    private StandingOrderStatus status;
    private Date startDate;
    private Date endDate;
    private Date nextExecutionDate;
    private Date lastExecutionDate;
    private int executionCount;
    private int maxExecutions;
    private String description;
}
