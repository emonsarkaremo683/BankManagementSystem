package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.StandingOrderFrequency;
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
public class StandingOrderRequest {
    private Long sourceAccountId;
    private String targetAccountNumber;
    private String targetAccountName;
    private BigDecimal amount;
    private StandingOrderFrequency frequency;
    private Date startDate;
    private Date endDate;
    private Integer maxExecutions;
    private String description;
}
