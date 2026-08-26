package com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response;

import com.elitetech_inc.ensarkbank.common.enums.ChequeBookStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ChequeBookResponse {
    private Long chequeBookId;
    private String bookSerialNumber;
    private int numberOfLeaves;
    private int startLeafNumber;
    private int endLeafNumber;
    private ChequeBookStatus status;
    private Long accountId;
    private String accountNumber;
    private LocalDate applicationDate;
    private LocalDate approvalDate;
    private LocalDate deliveryDate;
    private LocalDate activationDate;
    private LocalDate expiryDate;
    private String rejectionReason;
    private List<ChequeLeafResponse> leaves;
}
