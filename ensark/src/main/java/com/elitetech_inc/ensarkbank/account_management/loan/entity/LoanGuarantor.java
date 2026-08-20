package com.elitetech_inc.ensarkbank.account_management.loan.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "loan_guarantors")
@Data
public class LoanGuarantor extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    @JsonIgnore
    private Loan loan;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String nidNumber;

    @Column(nullable = false)
    private String relation;

    private String photoPath;
}
