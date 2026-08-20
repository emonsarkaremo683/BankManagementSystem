package com.elitetech_inc.ensarkbank.atm_management.atm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> {
    List<ATM> getATMByBranchId(Long branchId);
}
