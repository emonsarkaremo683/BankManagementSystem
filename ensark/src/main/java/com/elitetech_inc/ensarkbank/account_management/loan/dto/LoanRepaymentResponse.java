package com.elitetech_inc.ensarkbank.account_management.loan.dto;

import com.elitetech_inc.ensarkbank.common.enums.RepaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanRepaymentResponse {
    private Long id;
    private Long loanId;
    private Integer installmentNumber;
    private LocalDate dueDate;
    private BigDecimal principalComponent;
    private BigDecimal interestComponent;
    private BigDecimal emiAmount;
    private BigDecimal remainingBalanceAfter;
    private RepaymentStatus status;
    private LocalDate paidDate;
    private String transactionRef;
}
