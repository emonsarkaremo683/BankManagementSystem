package com.elitetech_inc.ensarkbank.account_management.account_interest.dto;

import com.elitetech_inc.ensarkbank.account_management.account_interest.AccountInterest;
import org.springframework.stereotype.Component;

@Component
public class AccountInterestMapper {

    public AccountInterestResponse toResponse(AccountInterest ai){
        AccountInterestResponse air = new AccountInterestResponse();
        air.setId(ai.getId());
        air.setAccountType(ai.getAccountType());
        air.setInterestRate(ai.getInterestRate());
        air.setTimeSpan(ai.getTimeSpan());
        air.setUpdated(ai.getUpdatedAt());
        return air;


    }
}
