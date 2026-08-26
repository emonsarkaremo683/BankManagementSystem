package com.elitetech_inc.ensarkbank.common.security;

import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.repository.BranchRepository;
import com.elitetech_inc.ensarkbank.common.enums.BranchType;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.auth_management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchAccessService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    public Optional<Long> resolveBranchId(Authentication auth) {
        if (auth == null) return Optional.empty();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .flatMap(user -> employeeRepository.findEmployeeByUser_Id(user.getId()))
                .filter(e -> e.getBranch() != null)
                .map(e -> e.getBranch().getId());
    }

    public boolean isHeadOffice(Authentication auth) {
        return resolveBranchId(auth)
                .flatMap(branchRepository::findById)
                .map(b -> b.getType() == BranchType.HEAD_OFFICE)
                .orElse(true);
    }

    public List<Long> getAccessibleBranchIds(Authentication auth) {
        if (isHeadOffice(auth)) {
            return null;
        }
        return resolveBranchId(auth)
                .map(this::collectBranchAndChildIds)
                .orElse(List.of());
    }

    private List<Long> collectBranchAndChildIds(Long branchId) {
        List<Long> result = new ArrayList<>();
        result.add(branchId);
        List<Branch> allBranches = branchRepository.findAll();
        collectChildBranchIds(branchId, allBranches, result);
        return result;
    }

    private void collectChildBranchIds(Long parentId, List<Branch> allBranches, List<Long> result) {
        for (Branch branch : allBranches) {
            if (branch.getParentBranch() != null && branch.getParentBranch().getId().equals(parentId)) {
                result.add(branch.getId());
                collectChildBranchIds(branch.getId(), allBranches, result);
            }
        }
    }
}
