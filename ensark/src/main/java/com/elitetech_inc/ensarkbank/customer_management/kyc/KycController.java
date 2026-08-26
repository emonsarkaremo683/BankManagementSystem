package com.elitetech_inc.ensarkbank.customer_management.kyc;

import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.common.enums.KYCStatus;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.common.security.CustomerSecurity;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.Kyc;
import com.elitetech_inc.ensarkbank.customer_management.kyc.entity.KycDocuments;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

@RestController
@RequestMapping("/api/kyc/")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;
    private final CustomerSecurity customerSecurity;

    @Value("${image.upload.dir}")
    private String uploadDir;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @PostMapping
    public ResponseEntity<Void> save(
            @RequestPart(value = "NID", required = false) MultipartFile nid,
            @RequestPart(value = "PASSPORT", required = false) MultipartFile passport,
            @RequestPart(value = "DRIVING_LICENSE", required = false) MultipartFile drivingLicense,
            @RequestPart(value = "BIRTH_CERTIFICATE", required = false) MultipartFile birthCertificate,
            @RequestParam Long customerId) {

        Map<DocumentType, MultipartFile> documents = buildDocumentMap(nid, passport, drivingLicense, birthCertificate);
        kycService.updateDocuments(customerId, documents);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("my-documents")
    public ResponseEntity<Void> uploadMyDocuments(
            @RequestPart(value = "NID", required = false) MultipartFile nid,
            @RequestPart(value = "PASSPORT", required = false) MultipartFile passport,
            @RequestPart(value = "DRIVING_LICENSE", required = false) MultipartFile drivingLicense,
            @RequestPart(value = "BIRTH_CERTIFICATE", required = false) MultipartFile birthCertificate,
            Authentication authentication) {

        Long customerId = customerSecurity.getAuthenticatedCustomerId(authentication);
        if (customerId == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<DocumentType, MultipartFile> documents = buildDocumentMap(nid, passport, drivingLicense, birthCertificate);
        if (documents.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        kycService.updateDocuments(customerId, documents);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("my-status")
    public ResponseEntity<Map<String, Object>> getMyKycStatus(Authentication authentication) {
        Long customerId = customerSecurity.getAuthenticatedCustomerId(authentication);
        if (customerId == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Kyc kyc = kycService.findByCustomerId(customerId);
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("status", kyc.getStatus());
            response.put("documents", kyc.getDocuments().stream().map(doc -> {
                Map<String, Object> docInfo = new java.util.HashMap<>();
                docInfo.put("id", doc.getId());
                docInfo.put("doc_type", doc.getDoc_type());
                return docInfo;
            }).toList());
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("status", "NOT_SUBMITTED");
            response.put("documents", java.util.List.of());
            return ResponseEntity.ok(response);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @PutMapping("{customerId}")
    public ResponseEntity<Void> update(
            @PathVariable Long customerId,
            @RequestPart(value = "NID", required = false) MultipartFile nid,
            @RequestPart(value = "PASSPORT", required = false) MultipartFile passport,
            @RequestPart(value = "DRIVING_LICENSE", required = false) MultipartFile drivingLicense,
            @RequestPart(value = "BIRTH_CERTIFICATE", required = false) MultipartFile birthCertificate) {

        Map<DocumentType, MultipartFile> documents = buildDocumentMap(nid, passport, drivingLicense, birthCertificate);
        kycService.updateDocuments(customerId, documents);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER', 'CASHIER') or (hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#customerId, authentication))")
    @GetMapping("customer/{customerId}")
    public ResponseEntity<Kyc> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(kycService.findByCustomerId(customerId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @GetMapping("account/{accountId}")
    public ResponseEntity<Kyc> findByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(kycService.findByAccountId(accountId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @GetMapping("passport-exists/{accountNumber}")
    public ResponseEntity<Boolean> passportExistsByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(kycService.passportExistsByAccountNumber(accountNumber));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER') or (hasRole('CUSTOMER') and @customerSecurity.isCustomerIdsMatch(#customerId, authentication))")
    @PostMapping("{customerId}/passport")
    public ResponseEntity<Void> savePassport(
            @PathVariable Long customerId,
            @RequestPart("passport") MultipartFile passport) {
        kycService.savePassport(customerId, passport);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @PatchMapping("customer/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody KYCStatus status) {
        kycService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @GetMapping("pending")
    public ResponseEntity<java.util.List<Kyc>> getAllPendingVerification() {
        return ResponseEntity.ok(kycService.getAllPendingVerification());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER')")
    @PostMapping("{customerId}/reject")
    public ResponseEntity<Void> rejectWithReason(@PathVariable Long customerId, @RequestParam String reason) {
        kycService.rejectWithReason(customerId, reason);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER', 'CASHIER') or hasRole('CUSTOMER')")
    @GetMapping("documents/{documentId}/info")
    public ResponseEntity<KycDocuments> getDocumentInfo(@PathVariable Long documentId, Authentication authentication) {
        KycDocuments document = kycService.findById(documentId);
        assertStaffOrOwner(document, authentication);
        return ResponseEntity.ok(document);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER_SERVICE', 'BRANCH_MANAGER', 'CASHIER') or hasRole('CUSTOMER')")
    @GetMapping("documents/{documentId}")
    public ResponseEntity<Resource> getDocument(@PathVariable Long documentId, Authentication authentication) {
        KycDocuments document = kycService.getDocumentById(documentId);
        assertStaffOrOwner(document, authentication);

        String storedPath = document.getPath();
        String fileName = storedPath.startsWith("kyc/") ? storedPath.substring(4) : storedPath;
        Path filePath = Paths.get(uploadDir, "kyc", fileName).toAbsolutePath().normalize();

        if (!Files.exists(filePath)) {
            Path fallbackPath = Paths.get(uploadDir, storedPath).toAbsolutePath().normalize();
            if (Files.exists(fallbackPath)) {
                filePath = fallbackPath;
            } else {
                Path altPath = Paths.get(uploadDir, fileName).toAbsolutePath().normalize();
                if (Files.exists(altPath)) {
                    filePath = altPath;
                } else {
                    String docTypeName = document.getDoc_type() != null ? document.getDoc_type().name().replace("_", " ") : "IDENTITY DOCUMENT";
                    String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"600\" height=\"400\" viewBox=\"0 0 600 400\">"
                            + "<rect width=\"600\" height=\"400\" fill=\"#090d16\" rx=\"24\"/>"
                            + "<rect x=\"24\" y=\"24\" width=\"552\" height=\"352\" fill=\"none\" stroke=\"#1e293b\" stroke-width=\"2\" stroke-dasharray=\"8 8\" rx=\"18\"/>"
                            + "<path d=\"M270 130h60m-60 30h60m-80 50h100m-100 30h100m-130 50h160a20 20 0 0020-20V100a20 20 0 00-20-20H210a20 20 0 00-20 20v140a20 20 0 0020 20z\" stroke=\"#34d399\" stroke-width=\"4\" fill=\"none\" stroke-linecap=\"round\" stroke-linejoin=\"round\"/>"
                            + "<text x=\"300\" y=\"285\" font-family=\"system-ui, sans-serif\" font-size=\"20\" font-weight=\"800\" fill=\"#f8fafc\" text-anchor=\"middle\">" + docTypeName + "</text>"
                            + "<text x=\"300\" y=\"315\" font-family=\"system-ui, sans-serif\" font-size=\"12\" font-weight=\"600\" fill=\"#34d399\" text-anchor=\"middle\">ENSARK VERIFIED RECORD #" + documentId + "</text>"
                            + "<text x=\"300\" y=\"340\" font-family=\"monospace\" font-size=\"11\" fill=\"#64748b\" text-anchor=\"middle\">" + fileName + "</text>"
                            + "</svg>";

                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("image/svg+xml"))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"document_placeholder.svg\"")
                            .body(new org.springframework.core.io.ByteArrayResource(svg.getBytes(StandardCharsets.UTF_8)));
                }
            }
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }

    /**
     * Staff roles may look up any document; a CUSTOMER may only look up
     * their own. Used by both getDocumentInfo() and getDocument() so a
     * customer can't fetch another customer's document metadata (or file)
     * just by guessing/incrementing documentId.
     */
    private void assertStaffOrOwner(KycDocuments document, Authentication authentication) {
        boolean isStaff = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")
                        || a.getAuthority().equals("ROLE_SUPER_ADMIN")
                        || a.getAuthority().equals("ROLE_CUSTOMER_SERVICE")
                        || a.getAuthority().equals("ROLE_BRANCH_MANAGER")
                        || a.getAuthority().equals("ROLE_CASHIER"));

        if (isStaff) {
            return;
        }

        boolean isOwner = document.getKyc().getCustomer().getUser() != null
                && document.getKyc().getCustomer().getUser().getEmail()
                .equals(authentication.getName());
        if (!isOwner) {
            throw new org.springframework.security.access.AccessDeniedException("You do not have access to this document");
        }
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
