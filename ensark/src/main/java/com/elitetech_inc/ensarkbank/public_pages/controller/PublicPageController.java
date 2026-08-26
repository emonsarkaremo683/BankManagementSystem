package com.elitetech_inc.ensarkbank.public_pages.controller;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import com.elitetech_inc.ensarkbank.currency_management.entity.ExchangeRate;
import com.elitetech_inc.ensarkbank.currency_management.repository.ExchangeRateRepository;
import com.elitetech_inc.ensarkbank.public_pages.dto.ExchangeRateResponse;
import com.elitetech_inc.ensarkbank.public_pages.dto.HeaderRatesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/")
@RequiredArgsConstructor
public class PublicPageController {

    private final ExchangeRateRepository exchangeRateRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("header-rates")
    public ResponseEntity<HeaderRatesResponse> getHeaderRates() {
        List<ExchangeRate> bdtRates = exchangeRateRepository.findByBaseCurrency(Currency.BDT);

        List<HeaderRatesResponse.CurrencyRate> rates = bdtRates.stream()
                .map(er -> new HeaderRatesResponse.CurrencyRate(
                        er.getTargetCurrency(),
                        er.getRate()))
                .collect(Collectors.toList());

        LocalDateTime lastUpdated = bdtRates.stream()
                .map(ExchangeRate::getFetchedAt)
                .filter(java.util.Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        HeaderRatesResponse response = new HeaderRatesResponse(
                Currency.BDT,
                lastUpdated,
                rates);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("exchange-rates")
    public ResponseEntity<ExchangeRateResponse> getAllExchangeRates() {
        List<ExchangeRate> bdtRates = exchangeRateRepository.findByBaseCurrency(Currency.BDT);

        List<ExchangeRateResponse.ExchangeRate> rates = bdtRates.stream()
                .map(er -> new ExchangeRateResponse.ExchangeRate(
                        er.getTargetCurrency(),
                        er.getRate(),
                        er.getRate().compareTo(BigDecimal.ZERO) > 0
                                ? BigDecimal.ONE.divide(er.getRate(), 6, java.math.RoundingMode.HALF_UP)
                                : BigDecimal.ZERO))
                .collect(Collectors.toList());

        LocalDateTime lastUpdated = bdtRates.stream()
                .map(ExchangeRate::getFetchedAt)
                .filter(java.util.Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());

        ExchangeRateResponse response = new ExchangeRateResponse(
                Currency.BDT,
                lastUpdated,
                rates);

        return ResponseEntity.ok(response);
    }
}
