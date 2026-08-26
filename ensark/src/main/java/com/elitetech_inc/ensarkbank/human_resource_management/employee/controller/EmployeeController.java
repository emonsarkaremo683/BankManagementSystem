package com.elitetech_inc.ensarkbank.human_resource_management.employee.controller;

import com.elitetech_inc.ensarkbank.common.enums.EmployeeStatus;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.request.EmployeeRequest;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.response.EmployeeResponse;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/employee/")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> save(
            @RequestPart("data") String data,
            @RequestPart(value = "profile", required = false) MultipartFile profilePicture) throws Exception {
        EmployeeRequest dto = objectMapper.readValue(data, EmployeeRequest.class);
        return new ResponseEntity<>(employeeService.save(dto, profilePicture), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "profile", required = false) MultipartFile profilePicture) throws Exception {
        EmployeeRequest dto = objectMapper.readValue(data, EmployeeRequest.class);
        return ResponseEntity.ok(employeeService.update(id, dto, profilePicture));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER', 'ACCOUNTANT', 'LOAN_OFFICER', 'CUSTOMER_SERVICE', 'ATM_MANAGER', 'AUDITOR')")
    @GetMapping("{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<EmployeeResponse>> getByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(employeeService.getByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("exists")
    public ResponseEntity<Boolean> existsByEmployeeEmail(@RequestParam String email) {
        return ResponseEntity.ok(employeeService.existsByEmployeeEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("email/{email}")
    public ResponseEntity<EmployeeResponse> findByEmployeeEmail(@PathVariable String email) {
        return ResponseEntity.ok(employeeService.findByEmployeeEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @PutMapping("{id}/profile")
    public ResponseEntity<EmployeeResponse> updateProfilePicture(
            @PathVariable Long id,
            @RequestPart("profile") MultipartFile profilePicture) {
        return ResponseEntity.ok(employeeService.updateProfilePicture(id, profilePicture));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @PatchMapping("{id}/status/{status}")
    public ResponseEntity<EmployeeResponse> updateStatus(
            @PathVariable Long id,
            @PathVariable EmployeeStatus status) {
        return ResponseEntity.ok(employeeService.updateStatus(id, status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER')")
    @GetMapping("search")
    public ResponseEntity<List<EmployeeResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(employeeService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping("{id}/reset-password")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        employeeService.resetPassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("{id}/designation")
    public ResponseEntity<EmployeeResponse> updateDesignation(
            @PathVariable Long id,
            @RequestParam com.elitetech_inc.ensarkbank.common.enums.Designation designation,
            @RequestParam com.elitetech_inc.ensarkbank.common.enums.Role role) {
        return ResponseEntity.ok(employeeService.updateDesignation(id, designation, role));
    }
}
