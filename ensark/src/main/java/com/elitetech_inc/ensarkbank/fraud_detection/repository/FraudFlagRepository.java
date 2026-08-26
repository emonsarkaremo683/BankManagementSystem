package com.elitetech_inc.ensarkbank.fraud_detection.repository;

import com.elitetech_inc.ensarkbank.common.enums.FraudFlagStatus;
import com.elitetech_inc.ensarkbank.common.enums.FraudRiskLevel;
import com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FraudFlagRepository extends JpaRepository<FraudFlag, Long> {

    List<FraudFlag> findAllByOrderByCreatedAtDesc();

    List<FraudFlag> findByStatus(FraudFlagStatus status);

    List<FraudFlag> findByUserId(Long userId);

    List<FraudFlag> findByRiskLevel(FraudRiskLevel riskLevel);

    @Query("SELECT COUNT(ff) FROM FraudFlag ff WHERE ff.userId = :userId AND ff.createdAt > :since")
    long countByUserIdSince(@Param("userId") Long userId, @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(ff) FROM FraudFlag ff WHERE ff.ipAddress = :ip AND ff.createdAt > :since")
    long countByIpSince(@Param("ip") String ip, @Param("since") LocalDateTime since);

    @Query("SELECT ff FROM FraudFlag ff WHERE ff.status = :status AND (:branchIds IS NULL OR ff.accountId IN (SELECT a.id FROM Account a WHERE a.branch.id IN :branchIds))")
    List<FraudFlag> findByStatusAndBranchIn(@Param("status") FraudFlagStatus status, @Param("branchIds") List<Long> branchIds);
}
