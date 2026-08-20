package com.elitetech_inc.ensarkbank.branch_management.branch.service;

import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.common.enums.BranchStatus;
import com.elitetech_inc.ensarkbank.common.enums.BranchType;

import java.util.List;

public interface BranchService {

    Branch create(Branch branch);

    Branch update(Long id, Branch branch);

    Branch findById(Long id);

    List<Branch> getAll();

    List<Branch> findByBranchType(BranchType type);

    List<Branch> findByStatus(BranchStatus status);

    List<Branch> search(String query);

    List<Branch> findByPoliceStationId(Long policeStationId);

    boolean branchCodeExists(String code);

    Branch deactivate(Long id);
}
