package com.elitetech_inc.ensarkbank.atm_management.atm;

import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm.dto.ATMResponse;
import com.elitetech_inc.ensarkbank.common.enums.ATMStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/atm")
@RequiredArgsConstructor
public class ATMController {


    private final ATMService atmService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER')")
    @PostMapping
    public ResponseEntity<ATMResponse> addATM(@RequestBody ATMRequest atm) {
        return ResponseEntity.ok(atmService.createATM(atm));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER')")
    @PutMapping("update/{id}")
    public ResponseEntity<ATMResponse> updateATM(@PathVariable Long id, @RequestBody ATMRequest atm) {
        return ResponseEntity.ok(atmService.updateATM(id, atm));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'BRANCH_MANAGER')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ATMResponse> updateATMStatus(
            @PathVariable Long id,
            @RequestParam ATMStatus status) {
        return ResponseEntity.ok(atmService.outOfServiceATM(id, status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'BRANCH_MANAGER')")
    @GetMapping("{id}")
    public ResponseEntity<Optional<ATMResponse>> getATM(@PathVariable Long id) {
        return ResponseEntity.ok(atmService.getATM(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'BRANCH_MANAGER')")
    @GetMapping("branch/{id}")
    public ResponseEntity<List<ATMResponse>> getATMByBranchId(@PathVariable Long id) {
        return ResponseEntity.ok(atmService.getATMByBranchId(id));
    }

    @PreAuthorize("permitAll()")
    @GetMapping({"", "/", "all"})
    public ResponseEntity<List<ATMResponse>> getATM() {
        return ResponseEntity.ok(atmService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteATM(@PathVariable Long id) {
        atmService.deleteATM(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'BRANCH_MANAGER')")
    @GetMapping("status/{status}")
    public ResponseEntity<List<ATMResponse>> findByStatus(@PathVariable ATMStatus status) {
        return ResponseEntity.ok(atmService.findByStatus(status));
    }
}