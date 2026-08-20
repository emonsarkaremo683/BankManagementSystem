package com.elitetech_inc.ensarkbank.account_management.account_holder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elitetech_inc.ensarkbank.account_management.account_holder.entity.AccountHolder;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
}
