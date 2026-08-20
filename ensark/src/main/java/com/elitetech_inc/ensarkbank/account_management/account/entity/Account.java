package com.elitetech_inc.ensarkbank.account_management.account.entity;

import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.AccountCategory;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.common.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "accounts")
@Data
public class Account extends BaseEntity {
    @Column(name = "acc_number", unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.BDT;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal availableBalance;

    @Column(precision = 19, scale = 4)
    private BigDecimal currentBalance;

    @Column(precision = 19, scale = 4)
    private BigDecimal holdBalance;

    @Enumerated(EnumType.STRING)
    private AccountCategory category;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;


    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AccountHolder> holders = new ArrayList<>();

    public void addHolder(AccountHolder holder) {
        holder.setAccount(this);
        this.holders.add(holder);
    }

    public void addHolders(List<AccountHolder> holders) {
        holders.forEach(this::addHolder);
    }

}
