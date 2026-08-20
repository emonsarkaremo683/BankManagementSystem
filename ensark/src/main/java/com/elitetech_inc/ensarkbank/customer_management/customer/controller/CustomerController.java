package com.elitetech_inc.ensarkbank.customer_management.customer.controller;

import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.security.BranchAccessService;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.request.CustomerRequest;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.response.CustomerResponse;
import com.elitetech_inc.ensarkbank.customer_management.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ObjectMapper objectMapper;
    private final BranchAccessService branchAccessService;
    private final CustomerSecurity customerSecurity;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @RequestPart("data") String data,
            @RequestPart(value = "profile", required = true) MultipartFile profilePicture,
            @RequestPart(value = "NID", required = false) MultipartFile nid,
            @RequestPart(value = "PASSPORT", required = false) MultipartFile passport,
            @RequestPart(value = "DRIVING_LICENSE", required = false) MultipartFile drivingLicense,
            @RequestPart(value = "BIRTH_CERTIFICATE", required = false) MultipartFile birthCertificate) throws Exception {

        CustomerRequest dto = objectMapper.readValue(data, CustomerRequest.class);
        Map<DocumentType, MultipartFile> documents = buildDocumentMap(nid, passport, drivingLicense, birthCertificate);

        return ResponseEntity.ok(customerService.create(dto, profilePicture, documents));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'ACCOUNTANT', 'AUDITOR')")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll(Authentication auth) {
        List<Long> branchIds = branchAccessService.getAccessibleBranchIds(auth);
        if (branchIds == null) {
            return ResponseEntity.ok(customerService.getAll());
        }
        return ResponseEntity.ok(customerService.getAllByBranchIds(branchIds));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("search")
    public ResponseEntity<List<CustomerResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(customerService.search(query));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("exists")
    public ResponseEntity<Boolean> customerEmailExists(@RequestParam String email) {
        return ResponseEntity.ok(customerService.customerEmailExists(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isEmailOwner(#email, authentication))")
    @GetMapping("email/{email}")
    public ResponseEntity<CustomerResponse> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.findByEmail(email));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER', 'ACCOUNTANT', 'AUDITOR') or (hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#id, authentication))")
    @GetMapping("{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#id, authentication))")
    @PutMapping("{id}")
    public ResponseEntity<CustomerResponse> updateByEmployee(
            @PathVariable Long id,
            @RequestPart("data") String data,
            @RequestPart(value = "profile", required = false) MultipartFile profile) throws Exception {
        CustomerRequest dto = objectMapper.readValue(data, CustomerRequest.class);
        return ResponseEntity.ok(customerService.updateByEmployee(id, dto, profile));
    }

    @PreAuthorize("(hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#id, authentication))")
    @PatchMapping("{id}/password")
    public ResponseEntity<CustomerResponse> updatePassword(
            @PathVariable Long id,
            @RequestParam String oldPass,
            @RequestParam String newPass) {
        return ResponseEntity.ok(customerService.updatePassword(id, oldPass, newPass));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE') or (hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#id, authentication))")
    @PutMapping("{id}/profile")
    public ResponseEntity<CustomerResponse> updateProfilePicture(
            @PathVariable Long id,
            @RequestPart("profile") MultipartFile profile) {
        return ResponseEntity.ok(customerService.updateProfilePicture(id, profile));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @PostMapping("{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        customerService.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("phone/{phone}")
    public ResponseEntity<CustomerResponse> findByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(customerService.findByPhone(phone));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE', 'CASHIER')")
    @GetMapping("phone-exists")
    public ResponseEntity<Boolean> phoneExistsCheck(@RequestParam String phone) {
        return ResponseEntity.ok(customerService.phoneExistsCheck(phone));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'BRANCH_MANAGER', 'CUSTOMER_SERVICE')")
    @GetMapping("status/{status}")
    public ResponseEntity<List<CustomerResponse>> findByStatus(@PathVariable com.elitetech_inc.ensarkbank.common.enums.CustomerStatus status) {
        return ResponseEntity.ok(customerService.findByStatus(status));
    }

    private Map<DocumentType, MultipartFile> buildDocumentMap(
            MultipartFile nid, MultipartFile passport,
            MultipartFile drivingLicense, MultipartFile birthCertificate) {
        Map<DocumentType, MultipartFile> documents = new EnumMap<>(DocumentType.class);
        if (nid != null && !nid.isEmpty()) documents.put(DocumentType.NID, nid);
        if (passport != null && !passport.isEmpty()) documents.put(DocumentType.PASSPORT, passport);
        if (drivingLicense != null && !drivingLicense.isEmpty()) documents.put(DocumentType.DRIVING_LICENSE, drivingLicense);
        if (birthCertificate != null && !birthCertificate.isEmpty()) documents.put(DocumentType.BIRTH_CERTIFICATE, birthCertificate);
        return documents;
    }
}
