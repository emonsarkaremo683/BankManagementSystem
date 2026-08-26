package com.elitetech_inc.ensarkbank.customer_management.kyc.entity;


import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "kyc")
public class Kyc extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private KYCStatus status;

    private String rejectionReason;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ToString.Exclude
    @OneToMany(mappedBy = "kyc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KycDocuments> documents = new ArrayList<>();

}
