package com.elitetech_inc.ensarkbank.atm_management.atm;

import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMResponse;
import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;

import java.util.List;
import java.util.Optional;

public interface ATMService {
    ATMResponse createATM(ATMRequest atmRequest);
    ATMResponse updateATM(Long id, ATMRequest atmRequest);
    ATMResponse outOfServiceATM(Long id, ATMStatus atmStatus);
    Optional<ATMResponse> getATM(Long id);
    List<ATMResponse> getATMByBranchId(Long branchId);
    List<ATMResponse> getAll();
    void deleteATM(Long id);
    List<ATMResponse> findByStatus(ATMStatus status);
}
