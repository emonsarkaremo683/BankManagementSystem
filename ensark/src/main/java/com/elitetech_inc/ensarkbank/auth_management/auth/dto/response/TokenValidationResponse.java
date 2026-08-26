package com.elitetech_inc.ensarkbank.auth_management.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {

    private boolean valid;
    private String email;
    private String role;
    private String purpose;
    private Long expiresAt;
    private String message;

    public static TokenValidationResponse valid(String email, String role, Long expiresAt) {
        return TokenValidationResponse.builder()
                .valid(true)
                .email(email)
                .role(role)
                .purpose(null)
                .expiresAt(expiresAt)
                .message("Token is valid")
                .build();
    }

    public static TokenValidationResponse invalid(String message) {
        return TokenValidationResponse.builder()
                .valid(false)
                .email(null)
                .role(null)
                .purpose(null)
                .expiresAt(null)
                .message(message)
                .build();
    }
}
