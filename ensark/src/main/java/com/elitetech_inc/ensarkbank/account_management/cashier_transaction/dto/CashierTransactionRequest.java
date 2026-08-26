package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto;

import com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.request.TransactionRequest;
import com.elitetech_inc.ensarkbank.common.enums.TransactionType;
import lombok.Data;

@Data
public class CashierTransactionRequest {
    private String checkNo;
    private Long branchId; // cashier's branch
    private String accountNumber;
    private String accountName;
    private TransactionType type;
    private String bankName;
    private Long employeeId;
    private String routingNumber; // receiver branch
    private TransactionRequest transactionRequest;
}
