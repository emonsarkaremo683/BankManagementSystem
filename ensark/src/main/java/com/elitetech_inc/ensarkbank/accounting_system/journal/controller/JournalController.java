package com.elitetech_inc.ensarkbank.accounting_system.journal.controller;

import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalRequest;
import com.elitetech_inc.ensarkbank.accounting_system.journal.dto.JournalResponse;
import com.elitetech_inc.ensarkbank.accounting_system.journal.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/journal/")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER')")
    @PostMapping
    public ResponseEntity<JournalResponse> create(@RequestBody JournalRequest request) {
        return ResponseEntity.ok(journalService.create(request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("customer/{email}")
    public ResponseEntity<List<JournalResponse>> findByCustomerEmail(@PathVariable String email) {
        return ResponseEntity.ok(journalService.findByCustomerEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping("account/{accountNumber}")
    public ResponseEntity<List<JournalResponse>> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(journalService.findByAccountNumber(accountNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("customer/{email}/span")
    public ResponseEntity<List<JournalResponse>> findByCustomerEmailAndTimeSpan(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(journalService.findByCustomerEmailAndTimeSpan(email, from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping("account/{accountNumber}/span")
    public ResponseEntity<List<JournalResponse>> findByAccountNumberAndTimeSpan(
            @PathVariable String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(journalService.findByAccountNumberAndTimeSpan(accountNumber, from, to));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping
    public ResponseEntity<List<JournalResponse>> getAll() {
        return ResponseEntity.ok(journalService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping("branch/{branchId}")
    public ResponseEntity<List<JournalResponse>> findByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(journalService.findByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping("search")
    public ResponseEntity<List<JournalResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(journalService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'AUDITOR')")
    @GetMapping("{journalId}")
    public ResponseEntity<JournalResponse> findById(@PathVariable Long journalId) {
        return ResponseEntity.ok(journalService.findById(journalId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ACCOUNTANT')")
    @PostMapping("{journalId}/reverse")
    public ResponseEntity<JournalResponse> reverseEntry(@PathVariable Long journalId) {
        return ResponseEntity.ok(journalService.reverseEntry(journalId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isAccountOwner(#accountNumber, authentication))")
    @GetMapping("account/{accountNumber}/export")
    public ResponseEntity<org.springframework.core.io.InputStreamResource> exportStatement(
            @PathVariable String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam String format) {

        java.io.ByteArrayInputStream bis = journalService.exportStatement(accountNumber, from, to, format);

        String filename = "statement_" + accountNumber + "." + ("pdf".equalsIgnoreCase(format) ? "pdf" : "xlsx");
        String contentType = "pdf".equalsIgnoreCase(format) ? "application/pdf" : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .body(new org.springframework.core.io.InputStreamResource(bis));
    }
}
