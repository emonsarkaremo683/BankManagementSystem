package com.elitetech_inc.ensarkbank.account_management.loan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRestructureRequest {
    @NotNull(message = "New tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    private Integer newTenureMonths;

    @NotNull(message = "New interest rate is required")
    @Min(value = 0, message = "Interest rate must be non-negative")
    private BigDecimal newAnnualInterestRate;

    @NotBlank(message = "Reason for restructure is required")
    private String reason;
}
