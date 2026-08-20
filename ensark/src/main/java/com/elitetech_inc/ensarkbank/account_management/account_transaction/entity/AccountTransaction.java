package com.elitetech_inc.ensarkbank.account_management.account_transaction.entity;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.Beneficiary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account_transactions")
@Data
public class AccountTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account receiver;

    private String receiverAccountNumber;
    private String receiverName;
    private String bankName;
    private String routingNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Transaction transaction;
}
