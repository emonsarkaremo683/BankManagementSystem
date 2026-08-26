package com.ensark.ensarkbank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpInitiateResponse {
    private Long otpReferenceId;
    private String maskedEmail;
    private Date expiresAt;
}
