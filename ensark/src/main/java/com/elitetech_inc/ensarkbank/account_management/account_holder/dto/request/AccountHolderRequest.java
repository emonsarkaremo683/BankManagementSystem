package com.elitetech_inc.ensarkbank.account_management.account_holder.dto.request;

import com.elitetech_inc.ensarkbank.common.enums.HolderType;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import lombok.Data;

@Data
public class AccountHolderRequest {
    private HolderType holderType;
    private Boolean canWithdraw;
    private Boolean canDeposit;
    private Boolean canApproveTransaction;

    private String signature;
    private Long customerId;
}
