package com.elitetech_inc.ensarkbank.auth_management.auth.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
