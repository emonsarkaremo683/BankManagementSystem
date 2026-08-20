package com.elitetech_inc.ensarkbank.account_management.card.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
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
