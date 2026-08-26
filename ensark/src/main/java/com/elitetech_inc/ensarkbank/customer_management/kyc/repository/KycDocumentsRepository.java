package com.elitetech_inc.ensarkbank.customer_management.kyc.repository;

import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.KycDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KycDocumentsRepository extends JpaRepository<KycDocuments, Long> {

    @Query("""
    select kd from KycDocuments kd
    join kd.kyc k
    join k.customer c
    where c.id = :customerId
""")
    List<KycDocuments> findKycDocumentsByCustomerId(@Param("customerId") Long customerId);

    @Query("""
    select kd from KycDocuments kd
    join fetch kd.kyc k
    join fetch k.customer c
    left join fetch c.user u
    where kd.id = :id
""")
    Optional<KycDocuments> findByIdWithCustomer(@Param("id") Long id);

}
