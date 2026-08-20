package com.elitetech_inc.ensarkbank.account_management.loan.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.RepaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "loan_repayments")
@Data
public class LoanRepayment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    @JsonIgnore
    private Loan loan;

    @Column(nullable = false)
    private Integer installmentNumber;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principalComponent;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal interestComponent;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal emiAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal remainingBalanceAfter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepaymentStatus status = RepaymentStatus.PENDING;

    private LocalDate paidDate;

    private String transactionRef;

}
