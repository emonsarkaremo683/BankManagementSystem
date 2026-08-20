package com.elitetech_inc.ensarkbank.common.exception;

import org.springframework.http.HttpStatus;

public class InsufficientCreditException extends RuntimeException {
    public InsufficientCreditException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
