package com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response;

import com.elitetech_inc.ensarkbank.common.enums.ChequeLeafStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ChequeLeafResponse {
    private Long leafId;
    private int leafNumber;
    private String chequeNumber;
    private BigDecimal amount;
    private String payeeName;
    private String remarks;
    private ChequeLeafStatus status;
    private LocalDate issueDate;
    private LocalDate clearanceDate;
    private LocalDate expiryDate;
    private String bounceReason;
    private String transactionReference;
    private Long chequeBookId;
    private String bookSerialNumber;
}
