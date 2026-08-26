package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.ChequeBookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChequeBookResponse {
    private Long chequeBookId;
    private String bookSerialNumber;
    private int numberOfLeaves;
    private int startLeafNumber;
    private int endLeafNumber;
    private ChequeBookStatus status;
    private Long accountId;
    private String accountNumber;
    private Date applicationDate;
    private Date approvalDate;
    private Date deliveryDate;
    private Date activationDate;
    private Date expiryDate;
    private String rejectionReason;
    private List<ChequeLeafResponse> leaves;
}
