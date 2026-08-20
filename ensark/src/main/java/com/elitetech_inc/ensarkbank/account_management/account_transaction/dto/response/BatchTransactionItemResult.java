package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchTransactionItemResult {
    private int index;
    private boolean success;
    private String referenceNo;
    private String errorMessage;
    private String senderAccountNumber;
    private String receiverAccountNumber;
}
