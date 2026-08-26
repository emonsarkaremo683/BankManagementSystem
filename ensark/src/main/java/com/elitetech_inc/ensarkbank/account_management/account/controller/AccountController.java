package com.elitetech_inc.ensarkbank.account_management.account.controller;

import com.elitetech_inc.ensarkbank.account_management.account.dto.request.AccountRequest;
import com.elitetech_inc.ensarkbank.account_management.account.dto.response.AccountResponse;
import com.elitetech_inc.ensarkbank.account_management.account.service.AccountService;
import com.elitetech_inc.ensarkbank.common.enums.AccountStatus;
import com.elitetech_inc.ensarkbank.common.security.BranchAccessService;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final CustomerSecurity customerSecurity;
    private final BranchAccessService branchAccessService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @PostMapping("create")
    public ResponseEntity<AccountResponse> create(
            @RequestPart("data") String data,
            @RequestPart(value = "signatures", required = true) List<MultipartFile> signatures,
            @RequestPart(value = "photo", required = true) MultipartFile photo,
            @RequestPart(value = "nid_front", required = true) MultipartFile nidFront,
            @RequestPart(value = "nid_back", required = true) MultipartFile nidBack,
            Authentication auth) throws Exception {

        AccountRequest dto = objectMapper.readValue(data, AccountRequest.class);
        Long customerId = customerSecurity.getAuthenticatedCustomerId(auth);
        if (customerId != null && dto.getAccountHolders() != null) {
            dto.getAccountHolders().forEach(h -> h.setCustomerId(customerId));
        }

        Map<String, MultipartFile> nominees = new HashMap<>();
        nominees.put("photo", photo);
        nominees.put("nid_front", nidFront);
        nominees.put("nid_back", nidBack);

        return new ResponseEntity<>(accountService.create(dto, signatures, nominees), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER')")
    @GetMapping("all/")
    public ResponseEntity<List<AccountResponse>> getAll(Authentication auth) {
        List<Long> branchIds = branchAccessService.getAccessibleBranchIds(auth);
        if (branchIds == null) {
            return ResponseEntity.ok(accountService.getAll());
        }
        return ResponseEntity.ok(accountService.getAllByBranchIds(branchIds));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#id, authentication))")
    @GetMapping("{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("email/{email}")
    public ResponseEntity<List<AccountResponse>> findByCustomerEmail(@PathVariable String email, Authentication auth) {
        return ResponseEntity.ok(accountService.findByCustomerEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("branch-vaults")
    public ResponseEntity<List<AccountResponse>> getAllBranchVault() {
        return ResponseEntity.ok(accountService.getAllBranchVault());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<AccountResponse>> findAccountByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(accountService.findAccountByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("branch-vault/{branchId}")
    public ResponseEntity<AccountResponse> findBranchVaultByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(accountService.findBranchVaultByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("search")
    public ResponseEntity<List<AccountResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(accountService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @GetMapping("number/{accountNumber}")
    public ResponseEntity<AccountResponse> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.findByAccountNumber(accountNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("exists")
    public ResponseEntity<Boolean> accountNumberExists(@RequestParam String accountNumber) {
        return ResponseEntity.ok(accountService.accountNumberExists(accountNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER')")
    @PutMapping("{id}")
    public ResponseEntity<AccountResponse> updateByEmployee(@PathVariable Long id, @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.updateByEmployee(id, request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PatchMapping("{id}/status/{status}")
    public ResponseEntity<AccountResponse> updateStatus(@PathVariable Long id, @PathVariable AccountStatus status) {
        return ResponseEntity.ok(accountService.updateStatus(id, status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @GetMapping("{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }
}
