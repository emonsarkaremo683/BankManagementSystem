package com.elitetech_inc.ensarkbank.currency_management.dto;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyResponse {
    private Currency currency;
    private BigDecimal rate;
}
