package com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.request;

import com.elitetech_inc.ensarkbank.common.enums.BeneficiaryType;
import lombok.Data;

@Data
public class BeneficiaryRequest {
    private String accNumber;
    private String name;
    private String provider;
    private String routingNumber;
    private BeneficiaryType beneficiaryType;
    private Long customerId;
}
