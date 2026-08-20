package com.elitetech_inc.ensarkbank.branch_management.branch.entity;

import java.util.ArrayList;
import java.util.List;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.atm_management.atm.ATM;
import com.elitetech_inc.ensarkbank.common.address.policestation.entity.PoliceStation;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.BranchStatus;
import com.elitetech_inc.ensarkbank.common.enums.BranchType;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "branches")
@Data

public class Branch extends BaseEntity {

    private String name;

    private String address;

    private String routingNumber;

    private String branchCode;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BranchType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BranchStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_branch_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Branch parentBranch;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "policeStationID")
    private PoliceStation policeStation;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "branch", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ATM> atms = new ArrayList<>();

    @OneToMany(mappedBy = "branch",  fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Account>accounts = new ArrayList<>();


}
