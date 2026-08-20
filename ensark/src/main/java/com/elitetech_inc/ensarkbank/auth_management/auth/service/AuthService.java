package com.elitetech_inc.ensarkbank.auth_management.auth.service;

import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.ForgetPasswordRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.LoginRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.request.ResetPasswordRequest;
import com.elitetech_inc.ensarkbank.auth_management.auth.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse<?> login(LoginRequest lr);
    LoginResponse<?> verifyMfaAndLogin(String email, String totpCode);
    LoginResponse<?> setupMfa(String email);
    void confirmMfaSetup(String email, String totpCode);
    void disableMfa(String email, String totpCode);
    void logout(String token);
    LoginResponse<?> refreshToken(String refreshToken);
    void sendVerificationEmail(String email);
    void verifyEmail(String token);
    void forgotPassword(ForgetPasswordRequest dto);
    void resetPassword(ResetPasswordRequest dto);
}
