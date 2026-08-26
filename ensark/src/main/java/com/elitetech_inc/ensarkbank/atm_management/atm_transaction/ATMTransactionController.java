package com.elitetech_inc.ensarkbank.atm_management.atm_transaction;

import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionRequest;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.ATMTransactionResponse;
import com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto.BalanceCheckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/atm-transactions")
@RequiredArgsConstructor
public class ATMTransactionController {

    private final ATMTransactionService atmTransactionService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<ATMTransactionResponse> transaction(@RequestBody ATMTransactionRequest request) {
        return ResponseEntity.ok(atmTransactionService.transaction(request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER')")
    @PostMapping("/{atmId}/refill")
    public ResponseEntity<ATMTransactionResponse> refill(
            @PathVariable Long atmId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(atmTransactionService.refill(atmId, amount));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'ACCOUNTANT', 'AUDITOR')")
    @GetMapping
    public ResponseEntity<List<ATMTransactionResponse>> getAll() {
        return ResponseEntity.ok(atmTransactionService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'ACCOUNTANT', 'AUDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ATMTransactionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(atmTransactionService.getById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'ACCOUNTANT', 'AUDITOR')")
    @GetMapping("/atm/{atmId}")
    public ResponseEntity<List<ATMTransactionResponse>> getByAtmId(@PathVariable Long atmId) {
        return ResponseEntity.ok(atmTransactionService.getByAtmId(atmId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CUSTOMER')")
    @PostMapping("/balance")
    public ResponseEntity<BigDecimal> checkBalance(@RequestBody BalanceCheckRequest request) {
        return ResponseEntity.ok(
                atmTransactionService.checkBalance(request.getCardNumber(), request.getPin()));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'ACCOUNTANT', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isCardOwner(#cardNumber, authentication))")
    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<List<ATMTransactionResponse>> getByCardNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(atmTransactionService.getByCardNumber(cardNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'ATM_MANAGER', 'ACCOUNTANT', 'AUDITOR')")
    @GetMapping("/date-range")
    public ResponseEntity<List<ATMTransactionResponse>> getByDateRange(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate from,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate to) {
        return ResponseEntity.ok(atmTransactionService.getByDateRange(from, to));
    }
}