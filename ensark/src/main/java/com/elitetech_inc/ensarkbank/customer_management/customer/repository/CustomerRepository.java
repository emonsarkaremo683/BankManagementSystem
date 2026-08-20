package com.elitetech_inc.ensarkbank.customer_management.customer.repository;

import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR c.phone LIKE CONCAT('%', :query, '%')")
    List<Customer> searchCustomers(@Param("query") String query);
    
    @Query("SELECT c FROM Customer c WHERE c.user.email = :email")
    Optional<Customer> findByUserEmail(@Param("email") String email);

    Optional<Customer> findCustomerByUser_Id(Long userId);

    @Query("SELECT DISTINCT c FROM Customer c WHERE c.id IN (SELECT ah.customer.id FROM AccountHolder ah JOIN ah.account a WHERE a.branch.id IN :branchIds)")
    List<Customer> findCustomersByBranchIds(@Param("branchIds") List<Long> branchIds);

    boolean existsByUserEmail(String email);

    Optional<Customer> findByPhone(String phone);

    boolean existsByPhone(String phone);

    @Query("SELECT c FROM Customer c JOIN c.kyc k WHERE k.status IN :statuses AND (:branchIds IS NULL OR c.id IN (SELECT ah.customer.id FROM AccountHolder ah JOIN ah.account a WHERE a.branch.id IN :branchIds) OR NOT EXISTS (SELECT ah FROM AccountHolder ah WHERE ah.customer.id = c.id))")
    List<Customer> findByKycStatusInAndBranchIn(@Param("statuses") List<KYCStatus> statuses, @Param("branchIds") List<Long> branchIds);
}
