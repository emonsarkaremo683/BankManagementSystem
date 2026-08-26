package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse {
    private Long loanId;
    private Long accountId;
    private String accountNumber;
    private BigDecimal principalAmount;
    private BigDecimal annualInterestRate;
    private Integer tenureMonths;
    private BigDecimal emiAmount;
    private BigDecimal totalPayable;
    private BigDecimal outstandingBalance;
    private BigDecimal disbursementCharge;
    private LoanStatus status;
    private Date applicationDate;
    private Date approvalDate;
    private Date disbursementDate;
    private Date nextDueDate;
    private String rejectionReason;
    private String disbursementTransactionRef;
    private List<GuarantorResponse> guarantors;
    private List<DocumentResponse> documents;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuarantorResponse {
        private Long id;
        private String name;
        private String phone;
        private String address;
        private String nidNumber;
        private String relation;
        private String photoPath;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentResponse {
        private Long id;
        private String fileName;
        private String originalFileName;
        private String contentType;
        private Long fileSize;
    }
}
