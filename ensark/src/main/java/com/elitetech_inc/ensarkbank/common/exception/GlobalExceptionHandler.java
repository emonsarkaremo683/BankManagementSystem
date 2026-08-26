package com.elitetech_inc.ensarkbank.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Builds the common response body and logs the exception server-side with a
     * correlation id, so failures are traceable in logs even though only a
     * generic message goes back to the client.
     */
    private ResponseEntity<Map<String, Object>> respond(HttpStatus status, String error, String message, Exception ex) {
        String correlationId = UUID.randomUUID().toString();
        log.error("[{}] {} - {}: {}", correlationId, status.value(), error, message, ex);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("correlationId", correlationId);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return respond(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), ex);
    }

    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleJpaEntityNotFound(jakarta.persistence.EntityNotFoundException ex) {
        return respond(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return respond(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        return respond(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), ex);
    }

    /**
     * Raised when a balance posting loses an optimistic-lock race against a
     * concurrent update and the automatic retry at the top-level transaction
     * boundary (see LedgerPostingService.applyEntry) has been exhausted.
     * Reported as a clean 409 instead of falling through to the generic
     * RuntimeException/500 handler, so clients can distinguish "retry me" from
     * "this request is broken".
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException ex) {
        return respond(HttpStatus.CONFLICT, "Conflict",
                "This record was updated concurrently by another request. Please retry.", ex);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), ex);
    }

    @ExceptionHandler(InsufficientCreditException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientCredit(InsufficientCreditException ex) {
        return respond(ex.getStatus(), "Insufficient Credit", ex.getMessage(), ex);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Map<String, Object>> handleTooManyRequests(TooManyRequestsException ex) {
        return respond(ex.getStatus(), "Too Many Requests", ex.getMessage(), ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        String correlationId = UUID.randomUUID().toString();
        log.error("[{}] {} - Validation Failed: {}", correlationId, HttpStatus.BAD_REQUEST.value(), fieldErrors, ex);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("message", "Invalid request data");
        body.put("fieldErrors", fieldErrors);
        body.put("correlationId", correlationId);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(org.springframework.security.authentication.BadCredentialsException ex) {
        return respond(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), ex);
    }

    @ExceptionHandler(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFound(org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
        return respond(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), ex);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        return respond(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), ex);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException ex) {
        return respond(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), ex);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        return respond(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), ex);
    }
}
