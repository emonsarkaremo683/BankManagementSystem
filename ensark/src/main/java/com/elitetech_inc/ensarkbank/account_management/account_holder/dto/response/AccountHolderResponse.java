package com.elitetech_inc.ensarkbank.account_management.account_holder.dto.response;

import com.elitetech_inc.ensarkbank.common.enums.HolderType;
import lombok.Data;

@Data
public class AccountHolderResponse {

    private Long id;
    private String accountHolderName;
    private HolderType holderType;
    private Boolean canWithdraw;
    private Boolean canDeposit;
    private String signature;
    private Boolean canApproveTransaction;
}
