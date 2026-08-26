package com.elitetech_inc.ensarkbank.fraud_detection.service;

import com.elitetech_inc.ensarkbank.common.enums.FraudFlagStatus;
import com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag;

import java.math.BigDecimal;
import java.util.List;

public interface FraudDetectionService {
    FraudFlag checkTransaction(Long userId, Long accountId, Long transactionId,
                               BigDecimal amount, String ipAddress, String deviceInfo);
    FraudFlag checkLogin(Long userId, String ipAddress, String deviceInfo, boolean success);
    FraudFlag reviewFlag(Long flagId, FraudFlagStatus newStatus, String reviewedBy, String reviewNotes);
    List<FraudFlag> getAllFlags();
    List<FraudFlag> getPendingFlags();
    List<FraudFlag> getFlagsByUser(Long userId);
    List<FraudFlag> getHighRiskFlags();
}
