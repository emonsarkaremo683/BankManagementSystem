package com.elitetech_inc.ensarkbank.atm_management.atm.dto;

import com.elitetech_inc.ensarkbank.atm_management.atm.ATM;
import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import org.springframework.stereotype.Component;

@Component
public class ATMMapper {

    public ATMResponse toResponse(ATM atm) {
        return ATMResponse.builder()
                .atmId(atm.getId())
                .status(atm.getStatus())
                .limit(atm.getLimit())
                .address(atm.getAddress())
                .routingNumber(atm.getAtmRouting())
                .accNumber(atm.getAccount().getAccountNumber())
                .type(atm.getAccount().getAccountType())
                .accountStatus(atm.getAccount().getAccountStatus())
                .availableBalance(atm.getAccount().getAvailableBalance())
                .branchName(atm.getBranch().getName())
                .policeStationName(atm.getBranch().getPoliceStation() != null ? atm.getBranch().getPoliceStation().getName() : null)
                .build();
    }

    public ATM toATM(ATMRequest atmRequest) {
        ATM atm = new ATM();
        atm.setStatus(atmRequest.getStatus());
        atm.setLimit(atmRequest.getLimit());
        atm.setAddress(atmRequest.getAddress());

        return atm;
    }


    public AccountStatus  toAccountStatus(ATMStatus status) {
        return switch (status) {
            case ACTIVE -> AccountStatus.ACTIVE;
            case MAINTENANCE -> AccountStatus.FREEZE;
            case OFFLINE -> AccountStatus.INACTIVE;
            case OUT_OF_SERVICE -> AccountStatus.CLOSED;
            default -> AccountStatus.BLOCKED;
        };
    }
}
