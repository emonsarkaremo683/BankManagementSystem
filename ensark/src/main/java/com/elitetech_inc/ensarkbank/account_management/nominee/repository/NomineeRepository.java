package com.elitetech_inc.ensarkbank.account_management.nominee.repository;

import com.elitetech_inc.ensarkbank.account_management.nominee.entity.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NomineeRepository extends JpaRepository<Nominee, Long> {
    Optional<Nominee> findNomineeByAccount_id(Long accountId);
}
