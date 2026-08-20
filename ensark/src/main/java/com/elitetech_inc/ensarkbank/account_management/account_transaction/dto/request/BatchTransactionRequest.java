package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchTransactionRequest {
    private List<BatchTransactionItem> transactions;
}
