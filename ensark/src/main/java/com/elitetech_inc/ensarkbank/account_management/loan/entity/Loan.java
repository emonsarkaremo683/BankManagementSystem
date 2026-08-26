package com.elitetech_inc.ensarkbank.account_management.loan.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.LoanStatus;
import com.elitetech_inc.ensarkbank.common.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "loan_applications")
public class Loan extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_id", nullable = false)
        private Account account;

        @Column(nullable = false, precision = 19, scale = 2)
        private BigDecimal principalAmount;

        @Column(nullable = false, precision = 5, scale = 2)
        private BigDecimal annualInterestRate;

        @Column(nullable = false)
        private Integer tenureMonths;

        @Column(nullable = false, precision = 19, scale = 2)
        private BigDecimal emiAmount;

        @Column(nullable = false, precision = 19, scale = 2)
        private BigDecimal outstandingBalance;

        @Column(nullable = false, precision = 19, scale = 2)
        private BigDecimal totalPayable;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private LoanStatus status = LoanStatus.PENDING;

        private LocalDate applicationDate = LocalDate.now();
        private LocalDate approvalDate;
        private LocalDate disbursementDate;
        private LocalDate nextDueDate;

        private String rejectionReason;

        private String disbursementTransactionRef;

        @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<LoanRepayment> repayments = new ArrayList<>();

        @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<LoanGuarantor> guarantors = new ArrayList<>();

        @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<LoanDocument> documents = new ArrayList<>();

        @Column(nullable = false, precision = 19, scale = 2)
        private BigDecimal disbursementCharge;

}
