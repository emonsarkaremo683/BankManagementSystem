package com.elitetech_inc.ensarkbank.dashboard;

import com.elitetech_inc.ensarkbank.common.security.BranchAccessService;
import com.elitetech_inc.ensarkbank.auth_management.user.repository.UserRepository;
import com.elitetech_inc.ensarkbank.common.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final BranchAccessService branchAccessService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'AUDITOR', 'ACCOUNTANT', 'LOAN_OFFICER', 'CASHIER', 'CUSTOMER_SERVICE', 'ATM_MANAGER')")
    @GetMapping("/stats")
    public ResponseEntity<DashboardResponse> getStats(Authentication auth) {
        List<Long> branchIds = branchAccessService.getAccessibleBranchIds(auth);
        Role role = userRepository.findByEmail(auth.getName())
                .map(com.elitetech_inc.ensarkbank.auth_management.user.entity.User::getRole)
                .orElse(Role.CUSTOMER);
        return new ResponseEntity<>(dashboardService.getDashboardData(branchIds, role), HttpStatus.OK);
    }
}
