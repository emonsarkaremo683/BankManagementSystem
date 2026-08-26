package com.elitetech_inc.ensarkbank.account_management.loan.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "loan_restructure_audit")
@Data
public class LoanRestructureAudit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal oldAnnualInterestRate;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal newAnnualInterestRate;

    @Column(nullable = false)
    private Integer oldTenureMonths;

    @Column(nullable = false)
    private Integer newTenureMonths;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal oldEmiAmount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal newEmiAmount;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    private String restructuredBy;

    private LocalDateTime restructuredAt = LocalDateTime.now();
}
