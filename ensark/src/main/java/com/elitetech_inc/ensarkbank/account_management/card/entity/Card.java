package com.elitetech_inc.ensarkbank.account_management.card.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cards")
@Data
public class Card extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardNetwork cardNetwork;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private String pinHash;
    private String cvv;

    @Enumerated(EnumType.STRING)
    private CardStatus status;


    private Date expiryDate;

    // BigDecimal, not double: these are money and must not be subject to
    // binary floating-point rounding error, especially since usage is
    // accumulated across many purchases.
    @Column(precision = 19, scale = 2)
    private BigDecimal dailyLimit = BigDecimal.ZERO;

    @Column(precision = 19, scale = 2)
    private BigDecimal monthlyLimit = BigDecimal.ZERO;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentDailyUsage = BigDecimal.ZERO;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentMonthlyUsage = BigDecimal.ZERO;
    private boolean isInternationalEnabled;
    private boolean isOnlineTransactionEnabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;
}
