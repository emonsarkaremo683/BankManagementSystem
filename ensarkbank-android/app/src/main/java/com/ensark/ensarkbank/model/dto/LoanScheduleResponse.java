package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.RepaymentStatus;
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
public class LoanScheduleResponse {
    private Long repaymentId;
    private Integer installmentNumber;
    private Date dueDate;
    private BigDecimal principalComponent;
    private BigDecimal interestComponent;
    private BigDecimal emiAmount;
    private BigDecimal remainingBalanceAfter;
    private RepaymentStatus status;
    private Date paidDate;
    private String transactionRef;
}
