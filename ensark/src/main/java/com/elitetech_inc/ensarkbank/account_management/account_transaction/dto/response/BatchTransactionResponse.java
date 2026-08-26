package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchTransactionResponse {
    private int totalRequested;
    private int totalSuccess;
    private int totalFailed;
    private List<BatchTransactionItemResult> results;
}
