package com.elitetech_inc.ensarkbank.account_management.account_interest;

import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestRequest;
import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestResponse;
import com.elitetech_inc.ensarkbank.common.enums.AccountType;

import java.util.List;

public interface AccountInterestService {

    AccountInterestResponse save(AccountInterestRequest request);

    AccountInterestResponse update(Long id, AccountInterestRequest request);

    List<AccountInterestResponse> getAll();

    AccountInterestResponse getById(Long id);

    boolean existsByCurrentMonth(Long accountTypeId);

    int sendInterestToEligibleAccount();

    int sendInterestToEligibleAccountById(Long id);

    void delete(Long id);

    AccountInterestResponse findByAccountId(Long accountId);
}
