package com.elitetech_inc.ensarkbank.branch_management.branch.repository;

import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.enums.BranchStatus;
import com.elitetech_inc.ensarkbank.common.enums.BranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByBranchCode(String branchCode);

    boolean existsByEmail(String email);

    Optional<Branch> findOneByEmail(String email);

    List<Branch> findByParentBranch_Id(Long parentBranchId);

    List<Branch> findByType(BranchType type);

    List<Branch> findByStatus(BranchStatus status);

    List<Branch> findByPoliceStationId(Long policeStationId);

    @Query("SELECT b FROM Branch b WHERE " +
            "LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.branchCode) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.phoneNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Branch> search(@Param("query") String query);
}
