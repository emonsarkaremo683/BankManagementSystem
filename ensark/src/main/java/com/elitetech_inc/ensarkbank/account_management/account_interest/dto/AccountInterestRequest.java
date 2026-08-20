package com.elitetech_inc.ensarkbank.account_management.account_interest.dto;

import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class AccountInterestRequest {
    private AccountType accountType;
    private BigDecimal interestRate;
    private Long timeSpan;
}
