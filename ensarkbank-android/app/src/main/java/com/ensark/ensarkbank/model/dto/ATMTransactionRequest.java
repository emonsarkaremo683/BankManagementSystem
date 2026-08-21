package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.ATMTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATMTransactionRequest {
    private Long atmId;
    private String cardNumber;
    private ATMTransactionType transactionType;
    private String pin;
    private TransactionRequest transactionRequest;
}
