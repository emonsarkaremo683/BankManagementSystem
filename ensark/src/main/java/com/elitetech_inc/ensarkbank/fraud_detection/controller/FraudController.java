package com.elitetech_inc.ensarkbank.fraud_detection.controller;

import com.elitetech_inc.ensarkbank.common.enums.FraudFlagStatus;
import com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag;
import com.elitetech_inc.ensarkbank.fraud_detection.service.FraudDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fraud/")
@RequiredArgsConstructor
public class FraudController {

    private final FraudDetectionService fraudDetectionService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'AUDITOR')")
    @GetMapping
    public ResponseEntity<List<FraudFlag>> getAllFlags() {
        return ResponseEntity.ok(fraudDetectionService.getAllFlags());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'AUDITOR')")
    @GetMapping("pending")
    public ResponseEntity<List<FraudFlag>> getPendingFlags() {
        return ResponseEntity.ok(fraudDetectionService.getPendingFlags());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'AUDITOR')")
    @GetMapping("high-risk")
    public ResponseEntity<List<FraudFlag>> getHighRiskFlags() {
        return ResponseEntity.ok(fraudDetectionService.getHighRiskFlags());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'AUDITOR')")
    @GetMapping("user/{userId}")
    public ResponseEntity<List<FraudFlag>> getFlagsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(fraudDetectionService.getFlagsByUser(userId));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'AUDITOR')")
    @PutMapping("{id}/review")
    public ResponseEntity<FraudFlag> reviewFlag(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        FraudFlagStatus status = FraudFlagStatus.valueOf(body.get("status"));
        String reviewedBy = body.get("reviewedBy");
        String reviewNotes = body.get("reviewNotes");
        return ResponseEntity.ok(fraudDetectionService.reviewFlag(id, status, reviewedBy, reviewNotes));
    }
}
