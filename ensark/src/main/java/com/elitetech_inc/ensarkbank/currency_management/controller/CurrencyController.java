package com.elitetech_inc.ensarkbank.currency_management.controller;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import com.elitetech_inc.ensarkbank.currency_management.dto.CurrencyResponse;
import com.elitetech_inc.ensarkbank.currency_management.entity.ExchangeRate;
import com.elitetech_inc.ensarkbank.currency_management.service.CurrencyConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currency/")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyConverterService currencyConverterService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER', 'CUSTOMER')")
    @GetMapping("convert")
    public ResponseEntity<Map<String, Object>> convert(
            @RequestParam Currency from,
            @RequestParam Currency to,
            @RequestParam BigDecimal amount) {
        BigDecimal result = currencyConverterService.convert(from, to, amount);
        return ResponseEntity.ok(Map.of(
                "from", from,
                "to", to,
                "amount", amount,
                "result", result
        ));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("rate")
    public ResponseEntity<ExchangeRate> getRate(@RequestParam Currency from, @RequestParam Currency to) {
        return ResponseEntity.ok(currencyConverterService.getRate(from, to));
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAll(@RequestParam Currency base){
        // By Default BDT will be base currency
        if (base == null){
            return ResponseEntity.ok(currencyConverterService.getAll(Currency.BDT));
        }
        return ResponseEntity.ok(currencyConverterService.getAll(base));
    }
}
