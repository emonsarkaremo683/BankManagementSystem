package com.elitetech_inc.ensarkbank.atm_management.atm.dto;

import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ATMRequest {

    private ATMStatus status;
    private BigDecimal balance;
    private BigDecimal limit;
    private String address;
    private Long branchId;
}
