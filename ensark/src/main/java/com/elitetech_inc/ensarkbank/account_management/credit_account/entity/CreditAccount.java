package com.elitetech_inc.ensarkbank.account_management.credit_account.entity;

import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.CreditAccountStatus;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "credit_accounts")
@Data
public class CreditAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", unique = true, nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal creditLimit;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal cashAdvanceLimit;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal outstandingBalance = BigDecimal.ZERO;

    @Column(precision = 19, scale = 4)
    private BigDecimal statementBalance;

    @Column(precision = 19, scale = 4)
    private BigDecimal minimumPaymentDue;

    private LocalDate paymentDueDate;

    @Column(nullable = false)
    private int billingCycleDay;

    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal apr;

    private LocalDate lastStatementDate;

    private boolean isInGracePeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditAccountStatus status = CreditAccountStatus.ACTIVE;

    @Transient
    public BigDecimal getAvailableCredit() {
        BigDecimal outstanding = outstandingBalance == null ? BigDecimal.ZERO : outstandingBalance;
        return creditLimit.subtract(outstanding);
    }
}
