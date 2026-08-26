package com.elitetech_inc.ensarkbank.account_management.cashier_transaction.controller;

import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.dto.CashierTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.cashier_transaction.service.CashierTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cashier-transaction/")
@RequiredArgsConstructor
public class CashierTransactionController {

    private final CashierTransactionService cashierTransactionService;

    @PreAuthorize("hasAnyRole('CASHIER', 'BRANCH_MANAGER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CashierTransactionResponse> create(
            @Valid @RequestBody CashierTransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cashierTransactionService.create(request));
    }

    @PreAuthorize("hasAnyRole('CASHIER', 'BRANCH_MANAGER', 'ADMIN')")
    @PostMapping("/{id}/reverse")
    public ResponseEntity<CashierTransactionResponse> reverse(@PathVariable Long id) {
        return ResponseEntity.ok(cashierTransactionService.reverse(id));
    }
}
