package com.elitetech_inc.ensarkbank.accounting_system.journal.entity;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.CashierTransaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.ATMTransaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Table(name = "join_helpers")
@Entity
@Data
public class JoinHelper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @OneToOne
    @JoinColumn(name = "atm_transaction_id")
    private ATMTransaction atmTransaction;

    @OneToOne
    @JoinColumn(name = "cashier_transaction_id")
    private CashierTransaction cashierTransaction;

    @OneToOne
    @JoinColumn(name = "account_transaction_id")
    private AccountTransaction accountTransaction;
}
