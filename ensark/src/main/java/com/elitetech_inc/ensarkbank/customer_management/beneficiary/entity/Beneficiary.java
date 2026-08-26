package com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.BeneficiaryType;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.*;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "beneficiaries")
@Data
public class Beneficiary extends BaseEntity {
    private String accNumber;
    private String name;
    private String provider;
    @Enumerated(EnumType.STRING)
    private BeneficiaryType beneficiaryType;

    private String routingNumber;

    private boolean isVerified = false;

    private boolean isBlocked = false;

    private String blockReason;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}
