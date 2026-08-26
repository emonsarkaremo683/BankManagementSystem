package com.elitetech_inc.ensarkbank.customer_management.beneficiary.controller;

import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.request.BeneficiaryRequest;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.dto.response.BeneficiaryResponse;
import com.elitetech_inc.ensarkbank.customer_management.beneficiary.service.BeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/beneficiary/")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<BeneficiaryResponse> add(@RequestBody BeneficiaryRequest request, Authentication auth) {
        Long customerId = customerSecurity.getAuthenticatedCustomerId(auth);
        if (customerId != null) {
            request.setCustomerId(customerId);
        }
        return ResponseEntity.ok(beneficiaryService.add(request));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @customerSecurity.isBeneficiaryOwner(#id, authentication)")
    @PutMapping("{id}")
    public ResponseEntity<BeneficiaryResponse> update(@PathVariable Long id, @RequestBody BeneficiaryRequest request) {
        return ResponseEntity.ok(beneficiaryService.update(id, request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER', 'CUSTOMER') and (hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("customer/email/{email}")
    public ResponseEntity<List<BeneficiaryResponse>> getByCustomerEmailReverse(@PathVariable String email) {
        return ResponseEntity.ok(beneficiaryService.getByCustomerEmailReverse(email));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#customerId, authentication)")
    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<BeneficiaryResponse>> getByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(beneficiaryService.getByCustomerId(customerId));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @customerSecurity.isBeneficiaryOwner(#id, authentication)")
    @GetMapping("{id}")
    public ResponseEntity<BeneficiaryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(beneficiaryService.findById(id));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @customerSecurity.isBeneficiaryOwner(#id, authentication)")
    @PostMapping("{id}/initiate-verify")
    public ResponseEntity<Map<String, String>> initiateVerification(@PathVariable Long id) {
        beneficiaryService.initiateVerification(id);
        return ResponseEntity.ok(Map.of("message", "Verification code sent to your registered email"));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @customerSecurity.isBeneficiaryOwner(#id, authentication)")
    @PostMapping("{id}/verify")
    public ResponseEntity<Map<String, String>> verify(@PathVariable Long id, @RequestParam String otpCode) {
        beneficiaryService.verify(id, otpCode);
        return ResponseEntity.ok(Map.of("message", "Beneficiary verified successfully"));
    }

    @PreAuthorize("hasRole('CUSTOMER') and @customerSecurity.isBeneficiaryOwner(#id, authentication)")
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        beneficiaryService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Beneficiary deleted successfully"));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/block")
    public ResponseEntity<BeneficiaryResponse> blockBeneficiary(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(beneficiaryService.blockBeneficiary(id, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/unblock")
    public ResponseEntity<BeneficiaryResponse> unblockBeneficiary(@PathVariable Long id) {
        return ResponseEntity.ok(beneficiaryService.unblockBeneficiary(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#accountId, authentication))")
    @GetMapping("account/{accountId}")
    public ResponseEntity<List<BeneficiaryResponse>> findByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(beneficiaryService.findByAccountId(accountId));
    }
}
