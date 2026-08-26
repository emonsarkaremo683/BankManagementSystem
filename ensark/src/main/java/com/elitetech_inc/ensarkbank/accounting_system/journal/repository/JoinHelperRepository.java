package com.elitetech_inc.ensarkbank.accounting_system.journal.repository;

import com.elitetech_inc.ensarkbank.accounting_system.journal.entity.JoinHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinHelperRepository extends JpaRepository<JoinHelper, Long> {
    JoinHelper findJoinHelperByTransaction_Id(Long transaction_id);

    @Query("SELECT j FROM JoinHelper j LEFT JOIN FETCH j.transaction t LEFT JOIN FETCH j.accountTransaction LEFT JOIN FETCH j.cashierTransaction LEFT JOIN FETCH j.atmTransaction ORDER BY t.createdAt DESC")
    List<JoinHelper> findAllWithTransactions();
}
