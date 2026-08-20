package com.elitetech_inc.ensarkbank.account_management.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PinChangeRequest {

    @NotBlank(message = "Old PIN is required")
    @Pattern(regexp = "^\\d{4,6}$", message = "PIN must be 4-6 digits")
    private String oldPin;

    @NotBlank(message = "New PIN is required")
    @Pattern(regexp = "^\\d{4,6}$", message = "PIN must be 4-6 digits")
    private String newPin;
}
