package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.EntryType;
import com.ensark.ensarkbank.model.enums.TransactionChannel;
import com.ensark.ensarkbank.model.enums.TransactionStatus;
import com.ensark.ensarkbank.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalResponse {
    private Long id;
    private Long transactionEntityId;
    private Date date;
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
