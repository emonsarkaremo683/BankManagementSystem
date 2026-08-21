package com.ensark.ensarkbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionRequest {
    private Long senderAccountId;
    private Long receiverAccountId;
    private String receiverAccountNumber;
    private String receiverName;
    private String bankName;
    private String routingNumber;
    private Long beneficiaryId;
    private TransactionRequest request;
}
