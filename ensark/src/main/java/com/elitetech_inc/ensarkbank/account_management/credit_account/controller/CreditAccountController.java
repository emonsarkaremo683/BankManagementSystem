package com.elitetech_inc.ensarkbank.account_management.credit_account.controller;

import com.elitetech_inc.ensarkbank.account_management.credit_account.entity.CreditAccount;
import com.elitetech_inc.ensarkbank.account_management.credit_account.repository.CreditAccountRepository;
import com.elitetech_inc.ensarkbank.account_management.credit_account.service.CreditPaymentService;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/credit-accounts/")
@RequiredArgsConstructor
public class CreditAccountController {

    private final CreditAccountRepository creditAccountRepository;
    private final CreditPaymentService creditPaymentService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') or (hasRole('CUSTOMER') and @customerSecurity.isCreditAccountOwner(#id, authentication))")
    @GetMapping("{id}/balance")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable Long id) {
        CreditAccount creditAccount = creditAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CreditAccount", id));

        Map<String, Object> response = new HashMap<>();
        response.put("creditLimit", creditAccount.getCreditLimit());
        response.put("outstandingBalance", creditAccount.getOutstandingBalance());
        response.put("availableCredit", creditAccount.getAvailableCredit());
        response.put("statementBalance", creditAccount.getStatementBalance());
        response.put("minimumPaymentDue", creditAccount.getMinimumPaymentDue());
        response.put("paymentDueDate", creditAccount.getPaymentDueDate());
        response.put("isInGracePeriod", creditAccount.isInGracePeriod());
        response.put("status", creditAccount.getStatus());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PostMapping("{id}/payment")
    public ResponseEntity<Map<String, Object>> makePayment(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Long sourceDepositAccountId = Long.valueOf(body.get("sourceDepositAccountId").toString());
        BigDecimal amount = new BigDecimal(body.get("amount").toString());

        CreditAccount updated = creditPaymentService.makePayment(id, sourceDepositAccountId, amount);

        Map<String, Object> response = new HashMap<>();
        response.put("outstandingBalance", updated.getOutstandingBalance());
        response.put("availableCredit", updated.getAvailableCredit());
        response.put("isInGracePeriod", updated.isInGracePeriod());
        response.put("message", "Payment processed successfully");

        return ResponseEntity.ok(response);
    }
}
