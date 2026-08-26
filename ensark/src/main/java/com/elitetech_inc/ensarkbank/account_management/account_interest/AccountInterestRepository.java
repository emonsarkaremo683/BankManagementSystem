package com.elitetech_inc.ensarkbank.account_management.account_interest;

import com.elitetech_inc.ensarkbank.common.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountInterestRepository extends JpaRepository<AccountInterest, Long> {
    Optional<AccountInterest> findByAccountType(AccountType accountType);
}
