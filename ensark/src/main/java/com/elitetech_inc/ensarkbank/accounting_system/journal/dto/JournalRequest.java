package com.elitetech_inc.ensarkbank.accounting_system.journal.dto;

import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class JournalRequest {
    private Long account_id;
    private String accountNumber;
    private EntryType entryType;
    private BigDecimal amount;
}
