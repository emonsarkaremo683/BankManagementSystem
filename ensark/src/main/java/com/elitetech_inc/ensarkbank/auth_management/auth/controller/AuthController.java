package com.elitetech_inc.ensarkbank.auth_management.auth.controller;

import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.ForgetPasswordRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.LoginRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.ResetPasswordRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.response.LoginResponse;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.response.TokenValidationResponse;
import com.elitetech_inc.ensarkbank.auth_management.auth.service.AuthService;
import com.elitetech_inc.ensarkbank.common.enums.DocumentType;
import com.elitetech_inc.ensarkbank.config.RateLimitConfig;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.request.CustomerRequest;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.response.CustomerResponse;
import com.elitetech_inc.ensarkbank.customer_management.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.EnumMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ObjectMapper objectMapper;
    private final RateLimitConfig rateLimitConfig;
    private final CustomerService customerService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
        String ip = getClientIp();
        String key = dto.getEmail() + ":" + ip;

        if (rateLimitConfig.isBlocked(key)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("message", "Too many login attempts. Try again later."));
        }

        try {
            LoginResponse<?> response = authService.login(dto);
            rateLimitConfig.resetAttempts(key);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            rateLimitConfig.recordAttempt(key);
            throw e;
        }
    }

    @PostMapping("/verify-mfa")
    public ResponseEntity<?> verifyMfa(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String totpCode = body.get("totpCode");

        if (email == null || totpCode == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "email and totpCode are required"));
        }

        LoginResponse<?> response = authService.verifyMfaAndLogin(email, totpCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/setup-mfa")
    public ResponseEntity<?> setupMfa(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "email is required"));
        }
        LoginResponse<?> response = authService.setupMfa(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-mfa")
    public ResponseEntity<?> confirmMfa(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String totpCode = body.get("totpCode");

        if (email == null || totpCode == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "email and totpCode are required"));
        }

        authService.confirmMfaSetup(email, totpCode);
        return ResponseEntity.ok(Map.of("message", "MFA enabled successfully"));
    }

    @PostMapping("/disable-mfa")
    public ResponseEntity<?> disableMfa(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String totpCode = body.get("totpCode");

        if (email == null || totpCode == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "email and totpCode are required"));
        }

        authService.disableMfa(email, totpCode);
        return ResponseEntity.ok(Map.of("message", "MFA disabled successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "refreshToken is required"));
        }
        LoginResponse<?> response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    private String getClientIp() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return "unknown";
        return attrs.getRequest().getRemoteAddr();
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestPart("data") String data,
                                                                    @RequestPart(value = "profile", required = false) MultipartFile profilePicture,
                                                                    @RequestPart(value = "NID",               required = false) MultipartFile nid,
                                                                    @RequestPart(value = "PASSPORT",          required = false) MultipartFile passport,
                                                                    @RequestPart(value = "DRIVING_LICENSE",   required = false) MultipartFile drivingLicense,
                                                                    @RequestPart(value = "BIRTH_CERTIFICATE", required = false) MultipartFile birthCertificate

    ) throws Exception {

        CustomerRequest dto = objectMapper.readValue(data, CustomerRequest.class);

        Map<DocumentType, MultipartFile> documents = new EnumMap<>(DocumentType.class);

        if (nid != null && !nid.isEmpty()) documents.put(DocumentType.NID, nid);
        if (passport != null && !passport.isEmpty()) documents.put(DocumentType.PASSPORT, passport);
        if (drivingLicense != null && !drivingLicense.isEmpty()) documents.put(DocumentType.DRIVING_LICENSE, drivingLicense);
        if (birthCertificate != null && !birthCertificate.isEmpty()) documents.put(DocumentType.BIRTH_CERTIFICATE, birthCertificate);
        return ResponseEntity.ok(customerService.create(dto, profilePicture, documents));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully. You can now log in.");
    }

    @PostMapping("/send-verification")
    public ResponseEntity<String> sendVerification(@RequestBody ForgetPasswordRequest dto) {
        authService.sendVerificationEmail(dto.getEmail());
        return ResponseEntity.ok("Verification email sent to " + dto.getEmail());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgetPasswordRequest dto) {
        authService.forgotPassword(dto);
        return ResponseEntity.ok("Password reset link sent to " + dto.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest dto) {
        authService.resetPassword(dto);
        return ResponseEntity.ok("Password reset successful. You can now log in with your new password.");
    }

    /**
     * Checks whether a token is still usable as a session credential.
     * Accepts the token via the standard Authorization: Bearer header, or as a
     * JSON body {"token": "..."}. Mirrors the checks performed by JwtAuthFilter:
     * signature/expiry, blacklist, and that it is an access (non-purpose) token.
     */
    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, String> body) {

        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else if (body != null && body.get("token") != null) {
            token = body.get("token");
        }

        return ResponseEntity.ok(authService.validateToken(token));
    }

}
