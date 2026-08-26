package com.elitetech_inc.ensarkbank.account_management.account_interest;

import com.elitetech_inc.ensarkbank.common.entity.BaseEntity;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "interest_data")
public class AccountInterest extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(unique = true)
    private AccountType accountType;
    private BigDecimal interestRate;
    private Long timeSpan;
}
