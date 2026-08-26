package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.HolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolderRequest {
    private HolderType holderType;
    private Boolean canWithdraw;
    private Boolean canDeposit;
    private Boolean canApproveTransaction;
    private String signature;
    private Long customerId;
}
