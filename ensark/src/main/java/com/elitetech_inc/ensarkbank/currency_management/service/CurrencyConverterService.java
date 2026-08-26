package com.elitetech_inc.ensarkbank.currency_management.service;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import com.elitetech_inc.ensarkbank.currency_management.dto.CurrencyMapper;
import com.elitetech_inc.ensarkbank.currency_management.dto.CurrencyResponse;
import com.elitetech_inc.ensarkbank.currency_management.entity.ExchangeRate;
import com.elitetech_inc.ensarkbank.currency_management.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyConverterService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyMapper currencyMapper;

    private static final MathContext MATH_CONTEXT = new MathContext(16, RoundingMode.HALF_UP);

    @Transactional(readOnly = true)
    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {
        if (from == to) {
            return amount;
        }

        Optional<ExchangeRate> directRate = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(from, to);
        if (directRate.isPresent()) {
            return amount.multiply(directRate.get().getRate()).setScale(4, RoundingMode.HALF_UP);
        }

        Optional<ExchangeRate> inverseRate = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(to, from);
        if (inverseRate.isPresent() && inverseRate.get().getRate().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal converted = amount.divide(inverseRate.get().getRate(), MATH_CONTEXT);
            return converted.setScale(4, RoundingMode.HALF_UP);
        }

        Optional<ExchangeRate> fromToUsd = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(from, Currency.USD);
        Optional<ExchangeRate> usdToTarget = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(Currency.USD, to);

        if (fromToUsd.isPresent() && usdToTarget.isPresent()) {
            BigDecimal usdAmount = amount.multiply(fromToUsd.get().getRate(), MATH_CONTEXT);
            BigDecimal result = usdAmount.multiply(usdToTarget.get().getRate(), MATH_CONTEXT);
            return result.setScale(4, RoundingMode.HALF_UP);
        }

        throw new RuntimeException("Exchange rate not available for " + from + " to " + to);
    }

    @Transactional
    public void updateRates(Currency baseCurrency, Map<Currency, BigDecimal> rates) {
        LocalDateTime now = LocalDateTime.now();
        rates.forEach((targetCurrency, rate) -> {
            if (baseCurrency != targetCurrency && rate.compareTo(BigDecimal.ZERO) > 0) {
                ExchangeRate exchangeRate = exchangeRateRepository
                        .findByBaseCurrencyAndTargetCurrency(baseCurrency, targetCurrency)
                        .orElse(new ExchangeRate());

                exchangeRate.setBaseCurrency(baseCurrency);
                exchangeRate.setTargetCurrency(targetCurrency);
                exchangeRate.setRate(rate);
                exchangeRate.setFetchedAt(now);
                exchangeRateRepository.save(exchangeRate);
            }
        });
        log.info("Updated {} exchange rates for base currency {}", rates.size(), baseCurrency);
    }

    @Transactional(readOnly = true)
    public ExchangeRate getRate(Currency from, Currency to) {
        return exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(from, to)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found for " + from + " to " + to));
    }

    @Transactional(readOnly = true)
    public List<CurrencyResponse> getAll(Currency base){
        return exchangeRateRepository.findByBaseCurrency(base)
                .stream().map(currencyMapper::toResponse).toList();
    }

}
