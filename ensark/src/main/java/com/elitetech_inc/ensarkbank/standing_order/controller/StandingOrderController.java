package com.elitetech_inc.ensarkbank.standing_order.controller;

import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderRequest;
import com.elitetech_inc.ensarkbank.standing_order.dto.StandingOrderResponse;
import com.elitetech_inc.ensarkbank.standing_order.entity.StandingOrder;
import com.elitetech_inc.ensarkbank.standing_order.service.StandingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/standing-orders/")
@RequiredArgsConstructor
public class StandingOrderController {

    private final StandingOrderService standingOrderService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER', 'CUSTOMER')")
    @PostMapping
    public ResponseEntity<StandingOrderResponse> createStandingOrder(@RequestBody StandingOrderRequest standingOrder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(standingOrderService.createStandingOrder(standingOrder));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isStandingOrderOwner(#id, authentication))")
    @PutMapping("{id}/cancel")
    public ResponseEntity<StandingOrderResponse> cancelStandingOrder(@PathVariable Long id) {
        return ResponseEntity.ok(standingOrderService.cancelStandingOrder(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isStandingOrderOwner(#id, authentication))")
    @PutMapping("{id}/pause")
    public ResponseEntity<StandingOrderResponse> pauseStandingOrder(@PathVariable Long id) {
        return ResponseEntity.ok(standingOrderService.pauseStandingOrder(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isStandingOrderOwner(#id, authentication))")
    @PutMapping("{id}/resume")
    public ResponseEntity<StandingOrderResponse> resumeStandingOrder(@PathVariable Long id) {
        return ResponseEntity.ok(standingOrderService.resumeStandingOrder(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isStandingOrderOwner(#id, authentication))")
    @GetMapping("{id}")
    public ResponseEntity<StandingOrderResponse> getStandingOrder(@PathVariable Long id) {
        return standingOrderService.getStandingOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#accountId, authentication))")
    @GetMapping("account/{accountId}")
    public ResponseEntity<List<StandingOrderResponse>> getStandingOrdersByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(standingOrderService.getStandingOrdersByAccountId(accountId));
    }

    // CASHIER can create/cancel/pause/resume/update standing orders (see the
    // rest of this controller) and the staff sidebar links CASHIER to this
    // list screen, so it needs read access here too — previously only
    // SUPER_ADMIN/ADMIN could load the list, so a cashier's own screen 403'd.
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER')")
    @GetMapping("active")
    public ResponseEntity<List<StandingOrderResponse>> getActiveOrders() {
        return ResponseEntity.ok(standingOrderService.getActiveOrders());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isStandingOrderOwner(#id, authentication))")
    @PutMapping("{id}")
    public ResponseEntity<StandingOrderResponse> updateStandingOrder(@PathVariable Long id, @RequestBody StandingOrderRequest standingOrder) {
        return ResponseEntity.ok(standingOrderService.updateStandingOrder(id, standingOrder));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isStandingOrderOwner(#id, authentication))")
    @GetMapping("{id}/history")
    public ResponseEntity<List<com.elitetech_inc.ensarkbank.accounting_system.transaction.dto.response.TransactionResponse>> getExecutionHistory(@PathVariable Long id) {
        return ResponseEntity.ok(standingOrderService.getExecutionHistory(id));
    }
}
