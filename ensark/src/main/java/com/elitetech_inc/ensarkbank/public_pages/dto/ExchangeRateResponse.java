package com.elitetech_inc.ensarkbank.public_pages.dto;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateResponse {

    private Currency baseCurrency;
    private LocalDateTime lastUpdated;
    private List<ExchangeRate> rates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeRate {
        private Currency currency;
        private BigDecimal buyRate;
        private BigDecimal sellRate;
    }
}
