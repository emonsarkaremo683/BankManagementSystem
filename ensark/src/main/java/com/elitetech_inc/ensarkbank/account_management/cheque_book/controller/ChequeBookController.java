package com.elitetech_inc.ensarkbank.account_management.cheque_book.controller;

import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.request.ChequeBookRequest;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeBookResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.response.ChequeLeafResponse;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.entity.ChequeLeafStatusHistory;
import com.elitetech_inc.ensarkbank.account_management.cheque_book.service.ChequeBookService;
import com.elitetech_inc.ensarkbank.common.security.BranchAccessService;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cheque/")
@RequiredArgsConstructor
public class ChequeBookController {

    private final ChequeBookService chequeBookService;
    private final BranchAccessService branchAccessService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#request.accountId, authentication))")
    @PostMapping("apply")
    public ResponseEntity<ChequeBookResponse> apply(@RequestBody ChequeBookRequest request) {
        return new ResponseEntity<>(chequeBookService.apply(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PutMapping("{id}")
    public ResponseEntity<ChequeBookResponse> update(@PathVariable Long id, @RequestBody ChequeBookRequest request) {
        return ResponseEntity.ok(chequeBookService.update(id, request));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("customer/email/{email}")
    public ResponseEntity<List<ChequeBookResponse>> findByCustomerEmail(@PathVariable String email, Authentication auth) {
        return ResponseEntity.ok(chequeBookService.findByCustomerEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("account/{accountNumber}")
    public ResponseEntity<List<ChequeBookResponse>> findByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(chequeBookService.findByAccountNumber(accountNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @GetMapping("search")
    public ResponseEntity<List<ChequeBookResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(chequeBookService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @GetMapping("all")
    public ResponseEntity<List<ChequeBookResponse>> getAll(Authentication auth) {
        return ResponseEntity.ok(chequeBookService.getAll());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/approve")
    public ResponseEntity<ChequeBookResponse> approve(@PathVariable Long id) {
        return ResponseEntity.ok(chequeBookService.approve(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/reject")
    public ResponseEntity<ChequeBookResponse> reject(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(chequeBookService.reject(id, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isChequeBookOwner(#id, authentication))")
    @GetMapping("{id}")
    public ResponseEntity<ChequeBookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(chequeBookService.getById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PostMapping("{chequeBookId}/leaves")
    public ResponseEntity<ChequeLeafResponse> issueLeaf(
            @PathVariable Long chequeBookId,
            @RequestParam BigDecimal amount,
            @RequestParam String payeeName,
            @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(chequeBookService.issueLeaf(chequeBookId, amount, payeeName, remarks));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("leaves/{leafId}")
    public ResponseEntity<ChequeLeafResponse> getLeafById(@PathVariable Long leafId) {
        return ResponseEntity.ok(chequeBookService.getLeafById(leafId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("leaves/number/{chequeNumber}")
    public ResponseEntity<ChequeLeafResponse> getLeafByChequeNumber(@PathVariable String chequeNumber) {
        return ResponseEntity.ok(chequeBookService.getLeafByChequeNumber(chequeNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("leaves/presented")
    public ResponseEntity<List<ChequeLeafResponse>> getAllPresented(Authentication auth) {
        if (branchAccessService.isHeadOffice(auth)) {
            return ResponseEntity.ok(chequeBookService.getAllPresented());
        }
        return branchAccessService.resolveBranchId(auth)
                .map(branchId -> ResponseEntity.ok(chequeBookService.getAllPresentedByBranchId(branchId)))
                .orElse(ResponseEntity.ok(List.of()));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CASHIER')")
    @GetMapping("leaves/presented/branch/{branchId}")
    public ResponseEntity<List<ChequeLeafResponse>> getAllPresentedByBranchId(@PathVariable Long branchId) {
        return ResponseEntity.ok(chequeBookService.getAllPresentedByBranchId(branchId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PostMapping("leaves/{leafId}/present")
    public ResponseEntity<ChequeLeafResponse> presentLeaf(@PathVariable Long leafId, @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(chequeBookService.presentLeaf(leafId, remarks));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PostMapping("leaves/{leafId}/clear")
    public ResponseEntity<ChequeLeafResponse> clearLeaf(@PathVariable Long leafId, @RequestParam String transactionReference) {
        return ResponseEntity.ok(chequeBookService.clearLeaf(leafId, transactionReference));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PostMapping("leaves/{leafId}/bounce")
    public ResponseEntity<ChequeLeafResponse> bounceLeaf(@PathVariable Long leafId, @RequestParam String reason) {
        return ResponseEntity.ok(chequeBookService.bounceLeaf(leafId, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isChequeLeafOwner(#leafId, authentication))")
    @PostMapping("leaves/{leafId}/stop-payment")
    public ResponseEntity<ChequeLeafResponse> stopPayment(@PathVariable Long leafId, @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(chequeBookService.stopPayment(leafId, remarks));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isChequeLeafOwner(#leafId, authentication))")
    @PostMapping("leaves/{leafId}/cancel")
    public ResponseEntity<ChequeLeafResponse> cancelLeaf(@PathVariable Long leafId, @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(chequeBookService.cancelLeaf(leafId, remarks));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("{chequeBookId}/leaves")
    public ResponseEntity<List<ChequeLeafResponse>> getLeavesByChequeBookId(@PathVariable Long chequeBookId) {
        return ResponseEntity.ok(chequeBookService.getLeavesByChequeBookId(chequeBookId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#customerId, authentication))")
    @GetMapping("customer/{customerId}/leaves")
    public ResponseEntity<List<ChequeLeafResponse>> getLeavesByCustomerId(
            @PathVariable Long customerId,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(chequeBookService.getLeavesByCustomerId(customerId, status));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("leaves/{leafId}/status-history")
    public ResponseEntity<List<ChequeLeafStatusHistory>> getLeafStatusHistory(@PathVariable Long leafId) {
        return ResponseEntity.ok(chequeBookService.getLeafStatusHistory(leafId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PutMapping("{id}/print")
    public ResponseEntity<ChequeBookResponse> markPrinted(@PathVariable Long id) {
        return ResponseEntity.ok(chequeBookService.markPrinted(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PutMapping("{id}/ready-for-delivery")
    public ResponseEntity<ChequeBookResponse> markReadyForDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(chequeBookService.markReadyForDelivery(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PutMapping("{id}/deliver")
    public ResponseEntity<ChequeBookResponse> markDelivered(@PathVariable Long id) {
        return ResponseEntity.ok(chequeBookService.markDelivered(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @PutMapping("{id}/activate")
    public ResponseEntity<ChequeBookResponse> activate(@PathVariable Long id) {
        return ResponseEntity.ok(chequeBookService.activate(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PutMapping("{id}/block")
    public ResponseEntity<ChequeBookResponse> block(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(chequeBookService.block(id, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PostMapping("{oldBookId}/reissue")
    public ResponseEntity<ChequeBookResponse> reissueChequeBook(@PathVariable Long oldBookId) {
        return ResponseEntity.ok(chequeBookService.reissueChequeBook(oldBookId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#chequeBookId, authentication))")
    @GetMapping("{chequeBookId}/unused-count")
    public ResponseEntity<Long> getUnusedLeafCount(@PathVariable Long chequeBookId) {
        return ResponseEntity.ok(chequeBookService.getUnusedLeafCount(chequeBookId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#accountId, authentication))")
    @GetMapping("account/{accountId}/summary")
    public ResponseEntity<java.util.Map<String, Long>> getChequeBookSummary(@PathVariable Long accountId) {
        return ResponseEntity.ok(chequeBookService.getChequeBookSummary(accountId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("{chequeBookId}/leaves/{leafNumber}")
    public ResponseEntity<ChequeLeafResponse> findByChequeBookIdAndLeafNumber(
            @PathVariable Long chequeBookId,
            @PathVariable int leafNumber) {
        return ResponseEntity.ok(chequeBookService.findByChequeBookIdAndLeafNumber(chequeBookId, leafNumber));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PostMapping("leaves/{leafId}/revert-presented")
    public ResponseEntity<ChequeLeafResponse> revertToIssued(
            @PathVariable Long leafId,
            @RequestParam String reason) {
        return ResponseEntity.ok(chequeBookService.revertToIssued(leafId, reason));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isChequeLeafOwner(#leafId, authentication))")
    @PostMapping("leaves/{leafId}/stop-payment-presented")
    public ResponseEntity<ChequeLeafResponse> stopPaymentOnPresented(
            @PathVariable Long leafId,
            @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(chequeBookService.stopPaymentOnPresented(leafId, remarks));
    }
}
