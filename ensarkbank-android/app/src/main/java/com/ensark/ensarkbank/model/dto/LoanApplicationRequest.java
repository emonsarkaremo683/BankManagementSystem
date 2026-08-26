package com.ensark.ensarkbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequest {
    private Long accountId;
    private BigDecimal principalAmount;
    private BigDecimal annualInterestRate;
    private Integer tenureMonths;
    private GuarantorRequest guarantor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuarantorRequest {
        private String name;
        private String phone;
        private String address;
        private String nidNumber;
        private String relation;
    }
}
