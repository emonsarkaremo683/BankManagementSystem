package com.elitetech_inc.ensarkbank.currency_management.scheduler;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import com.elitetech_inc.ensarkbank.currency_management.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateScheduler {

    private final CurrencyConverterService currencyConverterService;
    private final RestClient restClient;

    @Value("${currency.api.base-url:https://latest.currency-api.pages.dev/v1/currencies}")
    private String apiBaseUrl;

    @Scheduled(fixedRate = 21600000)
    public void fetchAndUpdateRates() {
        log.info("Starting scheduled exchange rate update...");
        try {
            for (Currency baseCurrency : Currency.values()) {
                fetchRatesForCurrency(baseCurrency);
            }
            log.info("Exchange rate update completed successfully");
        } catch (Exception e) {
            log.error("Failed to update exchange rates: {}", e.getMessage(), e);
        }
    }

    private void fetchRatesForCurrency(Currency baseCurrency) {
        try {
            String url = apiBaseUrl + "/" + baseCurrency.name().toLowerCase() + ".json";

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(Map.class);

            if (response == null || !response.containsKey(baseCurrency.name().toLowerCase())) {
                log.warn("No rate data for base currency: {}", baseCurrency);
                return;
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> ratesMap = (Map<String, Object>) response.get(baseCurrency.name().toLowerCase());
            if (ratesMap == null) return;

            Map<Currency, BigDecimal> rates = new HashMap<>();
            for (Currency target : Currency.values()) {
                Object rateValue = ratesMap.get(target.name().toLowerCase());
                if (rateValue != null) {
                    rates.put(target, new BigDecimal(rateValue.toString()));
                }
            }

            if (!rates.isEmpty()) {
                currencyConverterService.updateRates(baseCurrency, rates);
            }
        } catch (Exception e) {
            log.warn("Failed to fetch rates for {}: {}", baseCurrency, e.getMessage());
        }
    }
}
