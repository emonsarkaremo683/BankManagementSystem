package com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository;

import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.BeneficiaryVerification;
import com.elitetech_inc.ensarkbank.common.enums.OtpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiaryVerificationRepository extends JpaRepository<BeneficiaryVerification, Long> {

    @Query("SELECT bv FROM BeneficiaryVerification bv WHERE bv.beneficiary.id = :beneficiaryId AND bv.status = :status ORDER BY bv.createdAt DESC LIMIT 1")
    Optional<BeneficiaryVerification> findActiveVerification(@Param("beneficiaryId") Long beneficiaryId, @Param("status") OtpStatus status);
}
