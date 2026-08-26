package com.elitetech_inc.ensarkbank.customer_management.beneficiary.repository;

import com.elitetech_inc.ensarkbank.customer_management.beneficiary.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    List<Beneficiary> findBeneficiaryByCustomer_id(Long customerId);

    boolean existsByCustomerIdAndId(Long customerId, Long id);

    @Query("SELECT b FROM Beneficiary b WHERE b.customer.user.email = :email")
    List<Beneficiary> findByCustomerEmail(@Param("email") String email);
}
