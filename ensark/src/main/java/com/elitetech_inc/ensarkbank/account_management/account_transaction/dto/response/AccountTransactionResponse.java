package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountTransactionResponse {

    private Long id;
    private String transactionId;
    private String senderAccountNumber;
    private String senderName;
    private String receiverAccountNumber;
    private String receiverName;
    private String bankName;
    private String direction;


    private TransactionResponse response;
}
