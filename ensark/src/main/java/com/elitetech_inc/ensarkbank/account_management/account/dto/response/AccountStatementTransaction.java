package com.elitetech_inc.ensarkbank.account_management.account.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountStatementTransaction {
    private Long id;
    private LocalDateTime date;
    private String type;
    private String description;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
    private String reference;
    private String channel;
}
