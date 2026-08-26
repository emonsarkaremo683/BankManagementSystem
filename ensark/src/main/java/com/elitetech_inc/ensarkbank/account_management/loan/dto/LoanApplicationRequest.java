package com.elitetech_inc.ensarkbank.account_management.loan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanApplicationRequest {
    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Principal amount is required")
    @Min(value = 1, message = "Principal amount must be greater than 0")
    private BigDecimal principalAmount;

    @NotNull(message = "Interest rate is required")
    @Min(value = 0, message = "Interest rate must be non-negative")
    private BigDecimal annualInterestRate;

    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    private Integer tenureMonths;

    @NotNull(message = "Guarantor details are required")
    @Valid
    private GuarantorRequest guarantor;

    @Data
    public static class GuarantorRequest {
        @NotBlank(message = "Guarantor name is required")
        private String name;

        @NotBlank(message = "Guarantor phone is required")
        private String phone;

        @NotBlank(message = "Guarantor address is required")
        private String address;

        @NotBlank(message = "Guarantor NID number is required")
        private String nidNumber;

        @NotBlank(message = "Guarantor relation is required")
        private String relation;
    }
}
