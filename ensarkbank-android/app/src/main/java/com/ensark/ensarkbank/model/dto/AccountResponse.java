package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.AccountStatus;
import com.ensark.ensarkbank.model.enums.AccountType;
import com.ensark.ensarkbank.model.enums.NomineeRelation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private BigDecimal holdBalance;
    private String branchName;
    private String branchRoutingNumber;
    private String n_name;
    private String n_email;
    private NomineeRelation relation;
    private String n_phone;
    private String n_photo;
    private String n_nid_front;
    private String n_nid_back;
    @Builder.Default
    private List<AccountHolderResponse> holderResponses = new ArrayList<>();
}
