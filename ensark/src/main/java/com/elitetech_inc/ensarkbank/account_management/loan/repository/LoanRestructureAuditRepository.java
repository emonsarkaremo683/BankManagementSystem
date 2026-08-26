package com.elitetech_inc.ensarkbank.account_management.loan.repository;

import com.elitetech_inc.ensarkbank.account_management.loan.entity.LoanRestructureAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRestructureAuditRepository extends JpaRepository<LoanRestructureAudit, Long> {
    List<LoanRestructureAudit> findByLoanIdOrderByRestructuredAtDesc(Long loanId);

    @Query("SELECT a FROM LoanRestructureAudit a WHERE a.loan.id = :loanId ORDER BY a.restructuredAt DESC")
    List<LoanRestructureAudit> findHistoryByLoanId(@Param("loanId") Long loanId);
}
