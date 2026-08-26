package com.elitetech_inc.ensarkbank.account_management.account_interest;

import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestRequest;
import com.elitetech_inc.ensarkbank.account_management.account_interest.dto.AccountInterestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account-interest/")
@RequiredArgsConstructor
public class AccountInterestController {

    private final AccountInterestService interestService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public ResponseEntity<AccountInterestResponse> create(@RequestBody AccountInterestRequest request) {
        return ResponseEntity.ok(interestService.save(request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<AccountInterestResponse> update(@PathVariable Long id, @RequestBody AccountInterestRequest request) {
        return ResponseEntity.ok(interestService.update(id, request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("accrue")
    public ResponseEntity<Map<String, Object>> accrueAll() {
        int processed = interestService.sendInterestToEligibleAccount();
        return ResponseEntity.ok(Map.of(
                "message", "Interest accrual completed",
                "accountsCredited", processed
        ));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("{id}/accrue")
    public ResponseEntity<Map<String, Object>> accrueById(@PathVariable Long id) {
        int processed = interestService.sendInterestToEligibleAccountById(id);
        return ResponseEntity.ok(Map.of(
                "message", "Interest accrual completed for policy " + id,
                "accountsCredited", processed
        ));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<AccountInterestResponse>> getAll() {
        return ResponseEntity.ok(interestService.getAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{id}")
    public ResponseEntity<AccountInterestResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(interestService.getById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("exists")
    public ResponseEntity<Map<String, Boolean>> existsByCurrentMonth(@RequestParam Long accountTypeId) {
        return ResponseEntity.ok(Map.of(
                "exists", interestService.existsByCurrentMonth(accountTypeId)
        ));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        interestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("account/{accountId}")
    public ResponseEntity<AccountInterestResponse> findByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(interestService.findByAccountId(accountId));
    }
}
