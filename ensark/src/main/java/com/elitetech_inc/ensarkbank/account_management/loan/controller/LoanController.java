package com.elitetech_inc.ensarkbank.account_management.loan.controller;

import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationRequest;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanApplicationResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanRepaymentResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.dto.LoanScheduleResponse;
import com.elitetech_inc.ensarkbank.account_management.loan.service.LoanService;
import com.elitetech_inc.ensarkbank.common.enums.LoanStatus;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/loan/")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'CASHIER', 'CUSTOMER')")
    @PostMapping(value = "apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LoanApplicationResponse> apply(
            @RequestPart("data") @Valid LoanApplicationRequest request,
            @RequestPart(value = "documents", required = false) List<MultipartFile> documents,
            @RequestPart(value = "guarantorPhoto", required = false) MultipartFile guarantorPhoto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.apply(request, documents, guarantorPhoto));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER')")
    @PatchMapping("{id}/status")
    public ResponseEntity<LoanApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam LoanStatus status,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(loanService.updateStatus(id, status, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("customer/{email}")
    public ResponseEntity<List<LoanApplicationResponse>> findByCustomerEmail(@PathVariable String email, Authentication auth) {
        return ResponseEntity.ok(loanService.findByCustomerEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("account/{accountNumber}")
    public ResponseEntity<List<LoanApplicationResponse>> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(loanService.findByAccountNumber(accountNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("search")
    public ResponseEntity<List<LoanApplicationResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(loanService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @GetMapping("{id}")
    public ResponseEntity<LoanApplicationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.findById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("")
    public ResponseEntity<List<LoanApplicationResponse>> getAll() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @GetMapping("{id}/repayments")
    public ResponseEntity<List<LoanRepaymentResponse>> getRepaymentsByLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getRepaymentsByLoan(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @GetMapping("{id}/schedule")
    public ResponseEntity<List<LoanScheduleResponse>> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getSchedule(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'CASHIER', 'CUSTOMER')")
    @PostMapping("repayments/{repaymentId}/pay")
    public ResponseEntity<LoanRepaymentResponse> payInstallment(@PathVariable Long repaymentId) {
        return ResponseEntity.ok(loanService.payInstallment(repaymentId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("status/{status}")
    public ResponseEntity<List<LoanApplicationResponse>> findByStatus(@PathVariable LoanStatus status) {
        return ResponseEntity.ok(loanService.findByStatus(status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER')")
    @PostMapping("{id}/foreclose")
    public ResponseEntity<LoanApplicationResponse> closeLoanForeclosure(
            @PathVariable Long id,
            @RequestParam Long sweepFromAccountId) {
        return ResponseEntity.ok(loanService.closeLoanForeclosure(id, sweepFromAccountId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER')")
    @PostMapping("{id}/recalculate")
    public ResponseEntity<Void> recalculateEmiSchedule(@PathVariable Long id) {
        loanService.recalculateEmiSchedule(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'BRANCH_MANAGER', 'CASHIER', 'CUSTOMER')")
    @GetMapping("{id}/summary")
    public ResponseEntity<java.util.Map<String, Object>> getLoanSummary(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanSummary(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'CASHIER', 'CUSTOMER')")
    @PostMapping("repayments/{repaymentId}/pay-account")
    public ResponseEntity<LoanRepaymentResponse> payInstallmentByAccount(
            @PathVariable Long repaymentId,
            @RequestParam Long accountId) {
        return ResponseEntity.ok(loanService.payInstallmentByAccount(repaymentId, accountId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'CASHIER')")
    @PostMapping("repayments/{repaymentId}/pay-cashier")
    public ResponseEntity<LoanRepaymentResponse> payInstallmentByCashier(
            @PathVariable Long repaymentId,
            @RequestParam Long cashierId,
            @RequestParam Long branchId) {
        return ResponseEntity.ok(loanService.payInstallmentByCashier(repaymentId, cashierId, branchId));
    }
}