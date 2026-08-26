package com.elitetech_inc.ensarkbank.atm_management.atm_transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATMTransactionRepository extends JpaRepository<ATMTransaction, Long> {
    List<ATMTransaction> findByAtmId(Long atmId);
}
