package com.elitetech_inc.ensarkbank.util;

import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.BranchType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Central guard for branch-type based access rules.
 *
 * An AGENT_BANK is a limited-service outlet: it may only open customers and
 * accounts. It must NOT perform transactions, ATM operations, cashier
 * transactions or loans.
 *
 * Note: this project has no authentication layer, so the guarded operations
 * validate the *target branch's* type (passed in as branchId) rather than an
 * authenticated caller identity.
 */
@Component
@RequiredArgsConstructor
public class BranchValidator {

    private final BranchRepository branchRepository;

    public void assertNotAgentBank(Long branchId) {
        if (branchId == null) return;
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if (branch != null && branch.getType() == BranchType.AGENT_BANK) {
            throw new UnsupportedOperationException(
                    "Agent banks are limited to customer and account opening only. " +
                            "Transactions, ATM, cashier and loan operations are not permitted.");
        }
    }
}
