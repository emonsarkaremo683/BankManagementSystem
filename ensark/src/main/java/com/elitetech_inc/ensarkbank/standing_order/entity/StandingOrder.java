package com.elitetech_inc.ensarkbank.standing_order.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.StandingOrderFrequency;
import com.elitetech_inc.ensarkbank.common.enums.StandingOrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "standing_orders")
@Data
public class StandingOrder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @Column(nullable = false)
    private String targetAccountNumber;

    @Column(nullable = false)
    private String targetAccountName;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StandingOrderFrequency frequency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StandingOrderStatus status = StandingOrderStatus.ACTIVE;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextExecutionDate;
    private LocalDate lastExecutionDate;

    private int executionCount;
    private int maxExecutions;

    private String description;
}
