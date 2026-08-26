package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {
    private Currency currency;
    private BigDecimal rate;
}
