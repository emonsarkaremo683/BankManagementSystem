package com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.mapper;

import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.response.BeneficiaryResponse;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.Beneficiary;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component

public class BeneficiaryMapper {
    private final CustomerRepository customerRepository;

   public BeneficiaryResponse toResponse(Beneficiary b){
        BeneficiaryResponse br = new BeneficiaryResponse();
        br.setId(b.getId());
        br.setName(b.getName());
        br.setAccNumber(b.getAccNumber());
        br.setBeneficiaryType(b.getBeneficiaryType());
        br.setProvider(b.getProvider());
        br.setRoutingNumber(b.getRoutingNumber());
        br.setCustomerId(b.getCustomer().getId());
        Customer c = customerRepository.findById(b.getCustomer().getId()).orElseThrow();
        br.setCustomerName(c.getName());
        br.setVerified(b.isVerified());
        br.setBlocked(b.isBlocked());
        br.setBlockReason(b.getBlockReason());

        return br;
    }


}
