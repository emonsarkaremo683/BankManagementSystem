package com.elitetech_inc.ensarkbank.account_management.account.dto.request;

import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.request.AccountHolderRequest;
import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.common.enums.NomineeRelation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountRequest {
    private AccountType accountType;
    private BigDecimal availableBalance;
    private Long branchId;

    // nominee info
    private String n_name;
    private String n_email;
    private String n_phone;
    private NomineeRelation relation;
    private String n_photo;
    private String n_nid_front;
    private String n_nid_back;


    private List<AccountHolderRequest> accountHolders;
}
