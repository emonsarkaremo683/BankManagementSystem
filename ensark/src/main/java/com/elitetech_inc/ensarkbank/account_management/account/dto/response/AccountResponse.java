package com.elitetech_inc.ensarkbank.account_management.account.dto.response;

import com.elitetech_inc.ensarkbank.account_management.account_holder.dto.response.AccountHolderResponse;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import com.elitetech_inc.ensarkbank.common.enums.NomineeRelation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
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

    // nominee info
    private String n_name;
    private String n_email;
    private NomineeRelation relation;
    private String n_phone;
    private String n_photo;
    private String n_nid_front;
    private String n_nid_back;

    private List<AccountHolderResponse>holderResponses = new ArrayList<>();

}
