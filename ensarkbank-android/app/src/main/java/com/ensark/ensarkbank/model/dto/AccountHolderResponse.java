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
public class AccountHolderResponse {
    private Long id;
    private String accountHolderName;
    private HolderType holderType;
    private Boolean canWithdraw;
    private Boolean canDeposit;
    private String signature;
    private Boolean canApproveTransaction;
}
