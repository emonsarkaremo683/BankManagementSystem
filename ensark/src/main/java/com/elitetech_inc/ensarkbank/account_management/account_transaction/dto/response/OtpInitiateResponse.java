package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OtpInitiateResponse {
    private Long otpReferenceId;
    private String maskedEmail;
    private LocalDateTime expiresAt;
}
