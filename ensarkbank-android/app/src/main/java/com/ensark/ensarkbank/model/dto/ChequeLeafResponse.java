package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.ChequeLeafStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChequeLeafResponse {
    private Long leafId;
    private int leafNumber;
    private String chequeNumber;
    private BigDecimal amount;
    private String payeeName;
    private String remarks;
    private ChequeLeafStatus status;
    private Date issueDate;
    private Date clearanceDate;
    private Date expiryDate;
    private String bounceReason;
    private String transactionReference;
    private Long chequeBookId;
    private String bookSerialNumber;
}
