package com.elitetech_inc.ensarkbank.auth_management.auth.repository;

import com.elitetech_inc.ensarkbank.auth_management.auth.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    boolean existsByToken(String token);
}
