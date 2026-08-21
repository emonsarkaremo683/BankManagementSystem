package com.ensark.ensarkbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardUsageResponse {
    private Long cardId;
    private String cardNumber;
    private BigDecimal dailyLimit;
    private BigDecimal monthlyLimit;
    private BigDecimal currentDailyUsage;
    private BigDecimal currentMonthlyUsage;
    private BigDecimal dailyRemaining;
    private BigDecimal monthlyRemaining;
}
