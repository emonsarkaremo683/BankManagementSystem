package com.elitetech_inc.ensarkbank.customer_management.kyc.repository;

import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {

}
