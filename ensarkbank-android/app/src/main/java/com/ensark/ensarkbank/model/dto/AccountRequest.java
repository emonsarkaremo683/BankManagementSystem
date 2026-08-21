package com.ensark.ensarkbank.model.dto;

import com.ensark.ensarkbank.model.enums.AccountType;
import com.ensark.ensarkbank.model.enums.NomineeRelation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private AccountType accountType;
    private BigDecimal availableBalance;
    private Long branchId;
    private String n_name;
    private String n_email;
    private String n_phone;
    private NomineeRelation relation;
    private String n_photo;
    private String n_nid_front;
    private String n_nid_back;
    private List<AccountHolderRequest> accountHolders;
}
