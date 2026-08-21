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
public class ATMTransactionResponse {
    private Long ATMTransactionId;
    private ATMTransactionType transactionType;
    private String cardNumber;
    private String address;
    private TransactionResponse transactionResponse;
}
