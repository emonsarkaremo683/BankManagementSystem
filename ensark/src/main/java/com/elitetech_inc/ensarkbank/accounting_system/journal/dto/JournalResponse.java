package com.elitetech_inc.ensarkbank.accounting_system.journal.dto;

import com.elitetech_inc.ensarkbank.common.enums.EntryType;
import com.elitetech_inc.ensarkbank.common.enums.TransactionChannel;
import com.elitetech_inc.ensarkbank.common.enums.TransactionStatus;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class JournalResponse {
    private Long id;
    private Long transactionEntityId;
    private LocalDateTime date;
    private String transactionId;
    private String particulars;
    private String accountNumber;
    private String counterpartyAccountNumber;
    private String counterpartyName;
    private EntryType entryType;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionChannel channel;
    private TransactionStatus status;
    private String remarks;
}
