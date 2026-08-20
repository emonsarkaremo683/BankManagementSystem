package com.elitetech_inc.ensarkbank.account_management.loan.dto;

import com.elitetech_inc.ensarkbank.common.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
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

    private LocalDate applicationDate;
    private LocalDate approvalDate;
    private LocalDate disbursementDate;
    private LocalDate nextDueDate;

    private String rejectionReason;
    private String disbursementTransactionRef;

    private List<GuarantorResponse> guarantors;
    private List<DocumentResponse> documents;

    @Data
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
    public static class DocumentResponse {
        private Long id;
        private String fileName;
        private String originalFileName;
        private String contentType;
        private Long fileSize;
    }
}
