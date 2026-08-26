package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BatchTransactionItem {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private String remarks;
    private String transactionType;
    private String channel;
}
