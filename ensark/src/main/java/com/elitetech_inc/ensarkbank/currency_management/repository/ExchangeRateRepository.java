package com.elitetech_inc.ensarkbank.currency_management.repository;

import com.elitetech_inc.ensarkbank.currency_management.entity.ExchangeRate;
import com.elitetech_inc.ensarkbank.common.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByBaseCurrencyAndTargetCurrency(Currency base, Currency target);
    List<ExchangeRate> findByBaseCurrency(Currency base);
}
