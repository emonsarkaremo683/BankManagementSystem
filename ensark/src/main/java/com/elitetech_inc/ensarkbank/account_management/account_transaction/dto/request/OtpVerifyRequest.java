package com.elitetech_inc.ensarkbank.account_management.account_transaction.dto.request;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private Long otpReferenceId;
    private String otpCode;
}
