package com.elitetech_inc.ensarkbank.account_management.cheque_book.repository;

import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeafStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChequeLeafStatusHistoryRepository extends JpaRepository<ChequeLeafStatusHistory, Long> {
    List<ChequeLeafStatusHistory> findByChequeLeafIdOrderByCreatedAtAsc(Long chequeLeafId);
}
