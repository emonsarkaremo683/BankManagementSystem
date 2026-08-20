package com.elitetech_inc.ensarkbank.account_management.cashier_transaction;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.entity.AccountTransaction;
import com.elitetech_inc.ensarkbank.accounting_system.transaction.entity.Transaction;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cashier_transactions")
@Data
public class CashierTransaction extends BaseEntity {

    private String checkNo;

    @NotNull private String accountNumber;
    @NotNull private String accountName;
    @NotNull private String bankName;
    @NotNull private String routingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonIgnore
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false)
    @JsonIgnore
    private Employee employee;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;
}
