package com.elitetech_inc.ensarkbank.account_management.cheque_book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChequeBookRequest {
    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Number of leaves is required")
    @Min(value = 10, message = "Minimum 10 leaves required")
    private int numberOfLeaves;
}
