package com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto;


import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import com.elitetech_inc.ensarkbank.common.enums.ATMTransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ATMTransactionResponse {

    private Long ATMTransactionId;
    private ATMTransactionType transactionType;
    private String cardNumber;
    private String address;

    private TransactionResponse transactionResponse;

}
