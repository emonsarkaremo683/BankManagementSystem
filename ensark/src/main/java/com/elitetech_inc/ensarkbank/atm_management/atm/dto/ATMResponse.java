package com.elitetech_inc.ensarkbank.atm_management.atm.dto;

import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ATMResponse {

    private Long atmId;
    private ATMStatus status;
    private BigDecimal limit;
    private String address;
    private String routingNumber;
    private String accNumber;
    private AccountType type;
    private AccountStatus accountStatus;
    private BigDecimal availableBalance;
    private String branchName;
    private String policeStationName;
}
