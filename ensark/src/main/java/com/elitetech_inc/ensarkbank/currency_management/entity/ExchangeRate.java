package com.elitetech_inc.ensarkbank.currency_management.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "exchange_rates", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"base_currency", "target_currency"})
})
@Data
public class ExchangeRate extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "base_currency", nullable = false)
    private Currency baseCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_currency", nullable = false)
    private Currency targetCurrency;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal rate;

    private LocalDateTime fetchedAt;
}
