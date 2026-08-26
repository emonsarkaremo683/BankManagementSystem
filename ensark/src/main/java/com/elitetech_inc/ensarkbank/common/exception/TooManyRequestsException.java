package com.elitetech_inc.ensarkbank.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Raised when a request is blocked for rate-limiting / abuse-prevention
 * reasons — e.g. a HIGH risk fraud flag on a login attempt or transaction.
 */
public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.TOO_MANY_REQUESTS;
    }
}
