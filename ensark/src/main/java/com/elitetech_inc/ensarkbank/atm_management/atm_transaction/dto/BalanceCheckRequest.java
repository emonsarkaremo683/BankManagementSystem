package com.elitetech_inc.ensarkbank.atm_management.atm_transaction.dto;

import lombok.Data;

@Data
public class BalanceCheckRequest {
    private String cardNumber;
    private String pin;
}
