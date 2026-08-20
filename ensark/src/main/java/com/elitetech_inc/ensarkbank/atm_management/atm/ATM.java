package com.elitetech_inc.ensarkbank.atm_management.atm;


import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.card.entity.Card;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.ATMTransaction;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "atms")
public class ATM extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ATMStatus status;

    @Column(name = "`limit`")
    private BigDecimal limit;
    private String address;
    private String atmRouting;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchID")
    private Branch branch;

    @OneToMany(mappedBy = "atm", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ATMTransaction> atmTransections;

}
