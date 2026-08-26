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
public class HeaderRatesResponse {

    private Currency baseCurrency;
    private LocalDateTime lastUpdated;
    private List<CurrencyRate> rates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrencyRate {
        private Currency currency;
        private BigDecimal rate;
    }
}
