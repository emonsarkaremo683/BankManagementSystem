package com.elitetech_inc.ensarkbank.currency_management.dto;

import com.elitetech_inc.ensarkbank.common.enums.Currency;
import com.elitetech_inc.ensarkbank.currency_management.entity.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    @Mapping(target = "currency", source = "targetCurrency")
    @Mapping(target = "rate", source = "rate")
    CurrencyResponse toResponse(ExchangeRate exchangeRate);
}