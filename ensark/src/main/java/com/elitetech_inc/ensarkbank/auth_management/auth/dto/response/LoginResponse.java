package com.elitetech_inc.ensarkbank.auth_management.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse<E>{
    private String token;
    private String refreshToken;
    private String tokenType;
    private String name;
    private E user;
    private boolean mfaRequired;
    private String mfaSecret;
    private String mfaQrCode;
}
