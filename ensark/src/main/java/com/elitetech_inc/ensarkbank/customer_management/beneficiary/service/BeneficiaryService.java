package com.elitetech_inc.ensarkbank.customer_management.beneficiary.service;

import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.request.BeneficiaryRequest;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.response.BeneficiaryResponse;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryResponse add(BeneficiaryRequest request);

    BeneficiaryResponse update(Long id, BeneficiaryRequest request);

    List<BeneficiaryResponse> getByCustomerEmailReverse(String email);

    List<BeneficiaryResponse> getByCustomerId(Long customerId);

    BeneficiaryResponse findById(Long id);

    void delete(Long id);

    void initiateVerification(Long beneficiaryId);

    void verify(Long beneficiaryId, String otpCode);

    BeneficiaryResponse blockBeneficiary(Long id, String reason);

    BeneficiaryResponse unblockBeneficiary(Long id);

    List<BeneficiaryResponse> findByAccountId(Long accountId);
}
