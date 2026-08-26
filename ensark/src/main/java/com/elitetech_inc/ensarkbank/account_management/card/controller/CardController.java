package com.elitetech_inc.ensarkbank.account_management.card.controller;

import com.elitetech_inc.ensarkbank.account_management.card.dto.request.CardRequest;
import com.elitetech_inc.ensarkbank.account_management.card.dto.request.PinChangeRequest;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardResponse;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardPurchaseAuthorizationResponse;
import com.elitetech_inc.ensarkbank.account_management.card.dto.response.CardUsageResponse;
import com.elitetech_inc.ensarkbank.account_management.card.service.CardService;
import com.elitetech_inc.ensarkbank.account_management.hold_transaction.entity.HoldTransaction;
import com.elitetech_inc.ensarkbank.common.enums.CardNetwork;
import com.elitetech_inc.ensarkbank.common.enums.CardStatus;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/card/")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CUSTOMER')")
    @PostMapping("apply")
    public ResponseEntity<CardResponse> apply(@RequestBody CardRequest request, Authentication auth) {
        Long customerId = customerSecurity.getAuthenticatedCustomerId(auth);
        return ResponseEntity.ok(cardService.apply(request, customerId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("number/{cardNumber}")
    public ResponseEntity<CardResponse> findByCardNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(cardService.findByCardNumber(cardNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @GetMapping("exists")
    public ResponseEntity<Boolean> cardNumberExists(@RequestParam String cardNumber) {
        return ResponseEntity.ok(cardService.cardNumberExists(cardNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping("search")
    public ResponseEntity<List<CardResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(cardService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("customer/{email}")
    public ResponseEntity<List<CardResponse>> findByCustomerEmail(@PathVariable String email, Authentication auth) {
        return ResponseEntity.ok(cardService.findByCustomerEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("account/{accountNumber}")
    public ResponseEntity<List<CardResponse>> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(cardService.findByAccountNumber(accountNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PostMapping("{id}/multi-currency")
    public ResponseEntity<CardResponse> applyForMultiCurrency(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.applyForMultiCurrency(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PatchMapping("{id}/multi-currency")
    public ResponseEntity<CardResponse> activeMultiCurrency(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(cardService.activeMultiCurrency(id, active));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PatchMapping("{id}/network/{network}")
    public ResponseEntity<CardResponse> changeCardNetwork(@PathVariable Long id, @PathVariable CardNetwork network) {
        return ResponseEntity.ok(cardService.changeCardNetwork(id, network));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PatchMapping("{id}/type/{type}")
    public ResponseEntity<CardResponse> changeCardType(@PathVariable Long id, @PathVariable CardType type) {
        return ResponseEntity.ok(cardService.changeCardType(id, type));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isCardOwner(#id, authentication))")
    @PatchMapping("{id}/pin")
    public ResponseEntity<CardResponse> updatePin(@PathVariable Long id, @RequestBody PinChangeRequest request) {
        return ResponseEntity.ok(cardService.updatePin(id, request.getOldPin(), request.getNewPin()));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PatchMapping("{id}/status/{status}")
    public ResponseEntity<CardResponse> updateStatus(@PathVariable Long id, @PathVariable CardStatus status) {
        return ResponseEntity.ok(cardService.updateStatus(id, status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}")
    public ResponseEntity<CardResponse> updateByEmployee(@PathVariable Long id, @RequestBody CardRequest request) {
        return ResponseEntity.ok(cardService.updateByEmployee(id, request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isCardOwner(#id, authentication))")
    @GetMapping("{id}")
    public ResponseEntity<CardResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping
    public ResponseEntity<List<CardResponse>> getAll() {
        return ResponseEntity.ok(cardService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PostMapping("{id}/renew")
    public ResponseEntity<CardResponse> renewCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.renewCard(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isCardOwner(#id, authentication))")
    @PatchMapping("{id}/limit")
    public ResponseEntity<CardResponse> setTransactionLimit(
            @PathVariable Long id,
            @RequestParam BigDecimal dailyLimit,
            @RequestParam BigDecimal monthlyLimit) {
        return ResponseEntity.ok(cardService.setTransactionLimit(id, dailyLimit, monthlyLimit));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isCardOwner(#id, authentication))")
    @PostMapping("{id}/report-lost-stolen")
    public ResponseEntity<CardResponse> reportLostOrStolen(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(cardService.reportLostOrStolen(id, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PostMapping("{id}/authorize-purchase")
    public ResponseEntity<CardPurchaseAuthorizationResponse> authorizePurchase(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String merchantInfo) {
        HoldTransaction hold = cardService.authorizePurchase(id, amount, merchantInfo);
        return ResponseEntity.ok(CardPurchaseAuthorizationResponse.builder()
                .holdId(hold.getId())
                .authorizationReference(hold.getAuthorizationReference())
                .amount(hold.getAmount())
                .expiresAt(hold.getExpiresAt())
                .build());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isCardOwner(#id, authentication))")
    @GetMapping("{id}/usage")
    public ResponseEntity<CardUsageResponse> getUsage(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getUsage(id));
    }
}
