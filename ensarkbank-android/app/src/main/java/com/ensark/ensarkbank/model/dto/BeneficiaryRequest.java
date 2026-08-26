package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.BeneficiaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryRequest {
    private String accNumber;
    private String name;
    private String provider;
    private String routingNumber;
    private BeneficiaryType beneficiaryType;
    private Long customerId;
}
