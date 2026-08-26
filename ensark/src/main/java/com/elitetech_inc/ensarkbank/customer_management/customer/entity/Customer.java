package com.elitetech_inc.ensarkbank.customer_management.customer.entity;

import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.CustomerOccupation;
import com.elitetech_inc.ensarkbank.common.enums.Gender;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.Beneficiary;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.Kyc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
@Data

public class Customer extends BaseEntity {

    private String name;
    private String phone;

    @Enumerated(EnumType.STRING)
    private com.elitetech_inc.ensarkbank.common.enums.CustomerStatus status = com.elitetech_inc.ensarkbank.common.enums.CustomerStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private CustomerOccupation occupation;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String profile;


    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private Kyc kyc;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Beneficiary> beneficiaries = new ArrayList<>();





}
