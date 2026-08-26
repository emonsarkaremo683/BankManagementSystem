package com.elitetech_inc.ensarkbank.auth_management.auth.serviceimpl;

import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.ForgetPasswordRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.LoginRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.ResetPasswordRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.response.LoginResponse;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.response.TokenValidationResponse;
import com.elitetech_inc.ensarkbank.auth_management.auth.entity.TokenBlacklist;
import com.elitetech_inc.ensarkbank.auth_management.auth.repository.TokenBlacklistRepository;
import com.elitetech_inc.ensarkbank.auth_management.auth.security.EmailConfig;
import com.elitetech_inc.ensarkbank.auth_management.auth.security.JwtUtil;
import com.elitetech_inc.ensarkbank.auth_management.auth.service.AuthService;
import com.elitetech_inc.ensarkbank.auth_management.auth.service.TOTPService;
import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.auth_management.user.repository.UserRepository;
import com.elitetech_inc.ensarkbank.common.enums.FraudRiskLevel;
import com.elitetech_inc.ensarkbank.common.enums.Role;
import com.elitetech_inc.ensarkbank.common.exception.BadRequestException;
import com.elitetech_inc.ensarkbank.common.exception.ResourceNotFoundException;
import com.elitetech_inc.ensarkbank.common.exception.TooManyRequestsException;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.mapper.CustomerMapper;
import com.elitetech_inc.ensarkbank.customer_management.customer.dto.response.CustomerResponse;
import com.elitetech_inc.ensarkbank.customer_management.customer.entity.Customer;
import com.elitetech_inc.ensarkbank.customer_management.customer.repository.CustomerRepository;
import com.elitetech_inc.ensarkbank.customer_management.customer.service.CustomerService;
import com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag;
import com.elitetech_inc.ensarkbank.fraud_detection.service.FraudDetectionService;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.mapper.EmployeeMapper;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.dto.response.EmployeeResponse;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.entity.Employee;
import com.elitetech_inc.ensarkbank.human_resource_management.employee.repository.EmployeeRepository;
import com.elitetech_inc.ensarkbank.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CustomerMapper customerMapper;
    private final EmployeeMapper staffMapper;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository staffRepository;
    private final EmailConfig emailService;
    private final PasswordEncoder encoder;
    private final CustomerService customerService;
    private final TOTPService totpService;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final FraudDetectionService fraudDetectionService;
    private final Utils utils;

    private static final String ISSUER = "EnsarBank";

    @Override
    public LoginResponse<?> login(LoginRequest lr) {
        User user = getUser(lr);

        if (user.isMfaEnabled()) {
            return LoginResponse.builder()
                    .token(null)
                    .mfaRequired(true)
                    .user(null)
                    .build();
        }

        String token = jwtUtil.generateEmployeeToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole());

        if (user.getRole() == Role.CUSTOMER) {
            return toCustomerLogin(user, token, refreshToken);
        } else {
            return toStaffLogin(user, token, refreshToken);
        }
    }

    @Override
    public LoginResponse<?> verifyMfaAndLogin(String email, String totpCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isMfaEnabled()) {
            throw new BadRequestException("MFA is not enabled for this account");
        }

        if (!totpService.verifyCode(user.getTotpSecret(), totpCode)) {
            throw new BadCredentialsException("Invalid MFA code");
        }

        String token = jwtUtil.generateEmployeeToken(user.getEmail(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole());

        if (user.getRole() == Role.CUSTOMER) {
            return toCustomerLogin(user, token, refreshToken);
        } else {
            return toStaffLogin(user, token, refreshToken);
        }
    }

    @Override
    public LoginResponse<?> setupMfa(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.isMfaEnabled()) {
            throw new BadRequestException("MFA is already enabled");
        }

        String secret = totpService.generateSecret();
        user.setTotpSecret(secret);
        userRepository.save(user);

        String qrCodeData = totpService.generateQrCodeUri(secret, email, ISSUER);

        return LoginResponse.builder()
                .mfaSecret(secret)
                .mfaQrCode(qrCodeData)
                .build();
    }

    @Override
    public void confirmMfaSetup(String email, String totpCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.isMfaEnabled()) {
            throw new BadRequestException("MFA is already enabled");
        }

        if (user.getTotpSecret() == null) {
            throw new BadRequestException("MFA setup not initiated. Call setup-mfa first.");
        }

        if (!totpService.verifyCode(user.getTotpSecret(), totpCode)) {
            throw new BadRequestException("Invalid MFA code. Please try again.");
        }

        user.setMfaEnabled(true);
        userRepository.save(user);
        log.info("MFA enabled for user: {}", email);
    }

    @Override
    public void disableMfa(String email, String totpCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isMfaEnabled()) {
            throw new BadRequestException("MFA is not enabled");
        }

        if (!totpService.verifyCode(user.getTotpSecret(), totpCode)) {
            throw new BadRequestException("Invalid MFA code");
        }

        user.setMfaEnabled(false);
        user.setTotpSecret(null);
        userRepository.save(user);
        log.info("MFA disabled for user: {}", email);
    }

    @Override
    public TokenValidationResponse validateToken(String token) {
        if (token == null || token.isBlank()) {
            return TokenValidationResponse.invalid("Token is missing");
        }

        if (tokenBlacklistRepository.existsByToken(token)) {
            return TokenValidationResponse.invalid("Token has been revoked");
        }

        if (!jwtUtil.isTokenValid(token)) {
            return TokenValidationResponse.invalid("Token is invalid or expired");
        }

        // Single-purpose tokens (REFRESH / PASSWORD_RESET / EMAIL_VERIFICATION)
        // must not be treated as session credentials.
        if (jwtUtil.extractPurpose(token) != null) {
            return TokenValidationResponse.invalid("Token is not an access token");
        }

        return TokenValidationResponse.valid(
                jwtUtil.getEmail(token),
                jwtUtil.getRole(token).name(),
                jwtUtil.getExpiration(token).getTime()
        );
    }

    @Override
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (jwtUtil.isTokenValid(token)) {
            TokenBlacklist blacklisted = new TokenBlacklist();
            blacklisted.setToken(token);
            blacklisted.setEmail(jwtUtil.getEmail(token));
            blacklisted.setExpiresAt(jwtUtil.getExpiration(token).toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            tokenBlacklistRepository.save(blacklisted);
            log.info("Token blacklisted for user: {}", jwtUtil.getEmail(token));
        }
    }

    @Override
    public LoginResponse<?> refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BadRequestException("Refresh token is required");
        }

        if (!jwtUtil.isTokenValid(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        if (tokenBlacklistRepository.existsByToken(refreshToken)) {
            throw new BadRequestException("Refresh token has been revoked");
        }

        String email = jwtUtil.getEmail(refreshToken);
        Role role = jwtUtil.getRole(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        tokenBlacklistRepository.save(createBlacklistEntry(refreshToken, email));

        String token = jwtUtil.generateEmployeeToken(user.getEmail(), user.getRole());

        String newRefreshToken = jwtUtil.generateRefreshToken(email, role);

        if (user.getRole() == Role.CUSTOMER) {
            Customer c = customerRepository.findCustomerByUser_Id(user.getId()).orElseThrow();
            return LoginResponse.<CustomerResponse>builder()
                    .token(token)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .user(customerMapper.toResponse(c))
                    .build();
        } else {
            Employee s = staffRepository.findEmployeeByUser_Id(user.getId()).orElseThrow();
            return LoginResponse.<EmployeeResponse>builder()
                    .token(token)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .user(staffMapper.toResponse(s))
                    .build();
        }
    }

    private TokenBlacklist createBlacklistEntry(String token, String email) {
        TokenBlacklist entry = new TokenBlacklist();
        entry.setToken(token);
        entry.setEmail(email);
        entry.setExpiresAt(jwtUtil.getExpiration(token).toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        return entry;
    }

    private User getUser(LoginRequest lr){
        String ipAddress = utils.getClientIp();
        String deviceInfo = utils.getDeviceInfo();
        Long userIdForFlag = userRepository.findByEmail(lr.getEmail()).map(User::getId).orElse(null);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            lr.getEmail(),
                            lr.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            recordLoginAttempt(userIdForFlag, ipAddress, deviceInfo, false);
            throw new BadCredentialsException("Invalid email or password");
        }

        User user = userRepository.findByEmail(lr.getEmail()).orElseThrow();
        recordLoginAttempt(user.getId(), ipAddress, deviceInfo, true);
        return user;
    }

    /**
     * Wires FraudDetectionService.checkLogin into the login flow.
     * checkLogin is effectively a no-op on success (see
     * FraudDetectionServiceImpl — it only ever flags on repeated failures
     * from the same IP), so this mainly guards the failure path. A
     * HIGH/CRITICAL flag blocks the attempt outright: on top of
     * RateLimitConfig's per email+IP lockout in AuthController, this catches
     * credential-stuffing spread across many different accounts from the
     * same source. A MEDIUM/LOW flag is recorded for review but does not
     * block the login.
     */
    private void recordLoginAttempt(Long userId, String ipAddress, String deviceInfo, boolean success) {
        FraudFlag flag = fraudDetectionService.checkLogin(userId, ipAddress, deviceInfo, success);
        if (flag != null && (flag.getRiskLevel() == FraudRiskLevel.HIGH || flag.getRiskLevel() == FraudRiskLevel.CRITICAL)) {
            throw new TooManyRequestsException("Too many failed login attempts detected. Please try again later.");
        }
    }

    private LoginResponse<CustomerResponse> toCustomerLogin(User u, String token, String refreshToken){
        Customer c = customerRepository.findCustomerByUser_Id(u.getId()).orElseThrow(
                ()-> new UsernameNotFoundException("Customer data not found")
        );
        return LoginResponse.<CustomerResponse>builder()
                .token(token)
                .refreshToken(refreshToken)
                .name(c.getName())
                .tokenType("Bearer")
                .user(customerMapper.toResponse(c))
                .build();
    }

    private LoginResponse<EmployeeResponse> toStaffLogin(User u, String token, String refreshToken){
        Employee s = staffRepository.findEmployeeByUser_Id(u.getId()).orElseThrow(
                ()-> new UsernameNotFoundException("Staff data not found")
        );
        return LoginResponse.<EmployeeResponse>builder()
                .token(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .name(s.getName())
                .user(staffMapper.toResponse(s))
                .build();
    }

    @Override
    public void sendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (user.isActive()) {
            throw new BadRequestException("Account is already verified");
        }

        String token = jwtUtil.generateVerificationToken(user.getEmail());

        try {
            emailService.sendVerificationEmail(user.getEmail(), getName(user), token);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification email: " + e.getMessage());
        }
    }

    @Override
    public void verifyEmail(String token) {

        if (!jwtUtil.isValidForPurpose(token, "EMAIL_VERIFICATION")) {
            throw new RuntimeException("Invalid or expired verification link");
        }

        String email = jwtUtil.getEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isActive()) {
            throw new RuntimeException("Account is already verified");
        }

        user.setActive(true);
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgetPasswordRequest dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException(
                        "No account found with email: " + dto.getEmail()));

        String token = jwtUtil.generateResetToken(user.getEmail());

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), getName(user), token);
        } catch (Exception e) {
            log.error("Failed to send reset email to {}: {}", user.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Failed to send password reset email. Please try again later.");
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest dto) {

        if (!jwtUtil.isValidForPurpose(dto.getToken(), "PASSWORD_RESET")) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        if (dto.getNewPassword() == null || dto.getNewPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }

        String email = jwtUtil.getEmail(dto.getToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    private String getName(User user) {
        if(user.getRole() == Role.CUSTOMER) {
            Customer c = customerRepository.findCustomerByUser_Id(user.getId()).orElseThrow();
            return c.getName();
        }
        Employee s = staffRepository.findEmployeeByUser_Id(user.getId()).orElseThrow();
        return s.getName();
    }
}
