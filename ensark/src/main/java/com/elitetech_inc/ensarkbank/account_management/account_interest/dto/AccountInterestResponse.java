package com.elitetech_inc.ensarkbank.account_management.account_interest.dto;

import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AccountInterestResponse {
    private Long id;
    private AccountType accountType;
    private BigDecimal interestRate;
    private Long timeSpan;
    private LocalDateTime updated;

}
