package com.elitetech_inc.ensarkbank.account_management.account_transaction.controller;

import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.AccountTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.BatchTransactionRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request.OtpVerifyRequest;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.AccountTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.BatchTransactionResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response.OtpInitiateResponse;
import com.elitetech_inc.ensarkbank.account_management.account_transaction.service.AccountTransactionService;
import com.elitetech_inc.ensarkbank.common.security.BranchAccessService;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account-transaction/")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;
    private final CustomerSecurity customerSecurity;
    private final BranchAccessService branchAccessService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER')")
    @PostMapping
    public ResponseEntity<AccountTransactionResponse> save(@RequestBody AccountTransactionRequest atr, Authentication auth){
        return new ResponseEntity<>(accountTransactionService.save(atr), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#atr.senderAccountId, authentication))")
    @PostMapping("online/initiate")
    public ResponseEntity<OtpInitiateResponse> initiateOnlineTransaction(@RequestBody AccountTransactionRequest atr, Authentication auth){
        return new ResponseEntity<>(accountTransactionService.initiateOnlineTransaction(atr), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isOtpOwner(#req.otpReferenceId, authentication))")
    @PostMapping("online/verify")
    public ResponseEntity<AccountTransactionResponse> verifyOnlineTransaction(@RequestBody OtpVerifyRequest req, Authentication auth){
        return new ResponseEntity<>(accountTransactionService.verifyOnlineTransaction(req), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'AUDITOR')")
    @GetMapping({"all", "all/"})
    public ResponseEntity<List<AccountTransactionResponse>> getAll(){
        return new ResponseEntity<>(accountTransactionService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isAccountTransactionOwner(#id, authentication))")
    @GetMapping("{id:\\d+}")
    public ResponseEntity<AccountTransactionResponse> getById(@PathVariable Long id){
        return accountTransactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR')")
    @GetMapping("reference/{referenceNo}")
    public ResponseEntity<AccountTransactionResponse> getByReferenceNo(@PathVariable String referenceNo){
        return accountTransactionService.findByReferenceNo(referenceNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isAccountNumberOwner(#accountNumber, authentication))")
    @GetMapping("accountNumber/{accountNumber}")
    public ResponseEntity<List<AccountTransactionResponse>> findByAccountNumber(@PathVariable String accountNumber){
        return new ResponseEntity<>(
                accountTransactionService.findAllByAccountNumber(accountNumber),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CASHIER', 'CUSTOMER_SERVICE', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isOwner(#accountId, authentication))")
    @GetMapping("account/{accountId}")
    public ResponseEntity<List<AccountTransactionResponse>> findByAccountId(@PathVariable Long accountId){
        return new ResponseEntity<>(accountTransactionService.findByAccountId(accountId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CUSTOMER_SERVICE')")
    @PostMapping("transaction/{id}/reverse")
    public ResponseEntity<AccountTransactionResponse> reverse(@PathVariable Long id){
        return ResponseEntity.ok(accountTransactionService.reverseTransaction(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'ACCOUNTANT', 'CUSTOMER_SERVICE')")
    @PostMapping("reverse/{referenceNo}")
    public ResponseEntity<AccountTransactionResponse> reverseByReference(@PathVariable String referenceNo){
        return ResponseEntity.ok(accountTransactionService.reverseTransactionByReferenceNo(referenceNo));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'CASHIER')")
    @PostMapping("batch")
    public ResponseEntity<BatchTransactionResponse> processBatch(@RequestBody BatchTransactionRequest request) {
        return ResponseEntity.ok(accountTransactionService.processBatch(request));
    }

}
