package com.elitetech_inc.ensarkbank.account_management.card.controller;

import com.elitetech_inc.ensarkbank.account_management.card.entity.CardSettingsRequest;
import com.elitetech_inc.ensarkbank.account_management.card.service.CardSettingsRequestService;
import com.elitetech_inc.ensarkbank.common.enums.CardType;
import com.elitetech_inc.ensarkbank.common.enums.RequestStatus;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/card-settings-requests/")
@RequiredArgsConstructor
public class CardSettingsRequestController {

    private final CardSettingsRequestService requestService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<CardSettingsRequest> createRequest(
            @RequestBody Map<String, Object> body,
            Authentication auth) {
        Long cardId = Long.valueOf(body.get("cardId").toString());
        CardSettingsRequest.RequestType requestType = CardSettingsRequest.RequestType.valueOf(body.get("requestType").toString());
        boolean requestedValue = body.get("requestedValue") != null ? Boolean.parseBoolean(body.get("requestedValue").toString()) : false;
        Long customerId = customerSecurity.getAuthenticatedCustomerId(auth);

        CardType requestedCardType = null;
        if (body.get("requestedCardType") != null) {
            requestedCardType = CardType.valueOf(body.get("requestedCardType").toString());
        }

        return ResponseEntity.ok(requestService.createRequest(cardId, requestType, requestedValue, customerId, requestedCardType));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<CardSettingsRequest>> getMyRequests(@PathVariable Long customerId) {
        return ResponseEntity.ok(requestService.getRequestsByCustomerId(customerId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @GetMapping("pending")
    public ResponseEntity<List<CardSettingsRequest>> getPendingRequests() {
        return ResponseEntity.ok(requestService.getRequestsByStatus(RequestStatus.PENDING));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @GetMapping("card/{cardId}")
    public ResponseEntity<List<CardSettingsRequest>> getRequestsByCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(requestService.getRequestsByCardId(cardId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/approve")
    public ResponseEntity<CardSettingsRequest> approveRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.approveRequest(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/reject")
    public ResponseEntity<CardSettingsRequest> rejectRequest(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String reason = body.getOrDefault("reason", "");
        return ResponseEntity.ok(requestService.rejectRequest(id, reason));
    }
}
