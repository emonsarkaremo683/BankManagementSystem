package com.elitetech_inc.ensarkbank.account_management.loan.repository;

import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Long> {
    List<LoanRepayment> findByLoanIdOrderByInstallmentNumberAsc(Long loanId);

    @Query("SELECT COUNT(lr) > 0 FROM LoanRepayment lr JOIN lr.loan l JOIN l.account a JOIN a.holders h WHERE lr.id = :repaymentId AND h.customer.id = :customerId")
    boolean existsByRepaymentIdAndCustomerId(@Param("repaymentId") Long repaymentId, @Param("customerId") Long customerId);
}