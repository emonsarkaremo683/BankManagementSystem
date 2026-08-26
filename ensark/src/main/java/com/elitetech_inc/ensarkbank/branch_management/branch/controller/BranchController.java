package com.elitetech_inc.ensarkbank.branch_management.branch.controller;

import com.elitetech_inc.ensarkbank.branch_management.branch.entity.Branch;
import com.elitetech_inc.ensarkbank.branch_management.branch.service.BranchService;
import com.elitetech_inc.ensarkbank.common.enums.BranchStatus;
import com.elitetech_inc.ensarkbank.common.enums.BranchType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch/")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Branch> create(@RequestBody Branch branch) {
        return new ResponseEntity<>(branchService.create(branch), HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<Branch>> getAll() {
        return ResponseEntity.ok(branchService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'LOAN_OFFICER', 'ATM_MANAGER', 'AUDITOR', 'CUSTOMER')")
    @GetMapping("{id}")
    public ResponseEntity<Branch> findById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.findById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("type/{type}")
    public ResponseEntity<List<Branch>> findByBranchType(@PathVariable BranchType type) {
        return ResponseEntity.ok(branchService.findByBranchType(type));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("status/{status}")
    public ResponseEntity<List<Branch>> findByStatus(@PathVariable BranchStatus status) {
        return ResponseEntity.ok(branchService.findByStatus(status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("search")
    public ResponseEntity<List<Branch>> search(@RequestParam String query) {
        return ResponseEntity.ok(branchService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @PutMapping("{id}")
    public ResponseEntity<Branch> update(@PathVariable Long id, @RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.update(id, branch));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("police-station/{policeStationId}")
    public ResponseEntity<List<Branch>> findByPoliceStationId(@PathVariable Long policeStationId) {
        return ResponseEntity.ok(branchService.findByPoliceStationId(policeStationId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("exists")
    public ResponseEntity<Boolean> branchCodeExists(@RequestParam String code) {
        return ResponseEntity.ok(branchService.branchCodeExists(code));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("{id}/deactivate")
    public ResponseEntity<Branch> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.deactivate(id));
    }
}
