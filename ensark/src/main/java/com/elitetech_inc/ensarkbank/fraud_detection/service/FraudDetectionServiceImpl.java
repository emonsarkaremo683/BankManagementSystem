package com.elitetech_inc.ensarkbank.fraud_detection.service;

import com.elitetech_inc.ensarkbank.account_management.account.entity.Account;
import com.elitetech_inc.ensarkbank.account_management.account.repository.AccountRepository;
import com.elitetech_inc.ensarkbank.common.enums.FraudFlagStatus;
import com.elitetech_inc.ensarkbank.common.enums.FraudRiskLevel;
import com.elitetech_inc.ensarkbank.fraud_detection.entity.FraudFlag;
import com.elitetech_inc.ensarkbank.fraud_detection.repository.FraudFlagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionServiceImpl implements FraudDetectionService {

    private final FraudFlagRepository fraudFlagRepository;
    private final AccountRepository accountRepository;

    @Value("${fraud.velocity.max-transactions-per-hour:10}")
    private int maxTransactionsPerHour;

    @Value("${fraud.velocity.max-amount-per-hour:50000}")
    private BigDecimal maxAmountPerHour;

    @Value("${fraud.velocity.max-failed-logins-per-hour:5}")
    private int maxFailedLoginsPerHour;

    @Value("${fraud.high-value-threshold:100000}")
    private BigDecimal highValueThreshold;

    @Override
    @Transactional
    public FraudFlag checkTransaction(Long userId, Long accountId, Long transactionId,
                                       BigDecimal amount, String ipAddress, String deviceInfo) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        long recentFlags = fraudFlagRepository.countByUserIdSince(userId, oneHourAgo);
        if (recentFlags >= 3) {
            return createFlag(userId, transactionId, accountId, FraudRiskLevel.HIGH,
                    "Excessive fraud flags in last hour: " + recentFlags,
                    amount, ipAddress, deviceInfo);
        }

        if (amount.compareTo(highValueThreshold) > 0) {
            return createFlag(userId, transactionId, accountId, FraudRiskLevel.MEDIUM,
                    "High value transaction: " + amount,
                    amount, ipAddress, deviceInfo);
        }

        if (accountId != null) {
            Account account = accountRepository.findById(accountId).orElse(null);
            if (account != null) {
                BigDecimal dailyLimit = account.getAvailableBalance().multiply(new BigDecimal("0.5"));
                if (amount.compareTo(dailyLimit) > 0) {
                    return createFlag(userId, transactionId, accountId, FraudRiskLevel.MEDIUM,
                            "Transaction exceeds 50% of available balance",
                            amount, ipAddress, deviceInfo);
                }
            }
        }

        if (ipAddress != null) {
            long ipFlags = fraudFlagRepository.countByIpSince(ipAddress, oneHourAgo);
            if (ipFlags >= 5) {
                return createFlag(userId, transactionId, accountId, FraudRiskLevel.HIGH,
                        "Multiple fraud flags from same IP: " + ipFlags,
                        amount, ipAddress, deviceInfo);
            }
        }

        log.debug("Fraud check passed for userId={}, amount={}", userId, amount);
        return null;
    }

    @Override
    @Transactional
    public FraudFlag checkLogin(Long userId, String ipAddress, String deviceInfo, boolean success) {
        if (success) {
            return null;
        }

        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        long ipFlags = fraudFlagRepository.countByIpSince(ipAddress, oneHourAgo);

        if (ipFlags >= maxFailedLoginsPerHour) {
            return createFlag(userId, null, null, FraudRiskLevel.HIGH,
                    "Excessive failed login attempts from IP: " + ipFlags,
                    BigDecimal.ZERO, ipAddress, deviceInfo);
        }

        return null;
    }

    @Override
    @Transactional
    public FraudFlag reviewFlag(Long flagId, FraudFlagStatus newStatus, String reviewedBy, String reviewNotes) {
        FraudFlag flag = fraudFlagRepository.findById(flagId)
                .orElseThrow(() -> new RuntimeException("Fraud flag not found"));

        flag.setStatus(newStatus);
        flag.setReviewedBy(reviewedBy);
        flag.setReviewNotes(reviewNotes);

        FraudFlag saved = fraudFlagRepository.save(flag);
        log.info("Fraud flag {} reviewed: {} -> {} by {}", flagId, flag.getStatus(), newStatus, reviewedBy);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudFlag> getAllFlags() {
        return fraudFlagRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudFlag> getPendingFlags() {
        return fraudFlagRepository.findByStatus(FraudFlagStatus.PENDING);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudFlag> getFlagsByUser(Long userId) {
        return fraudFlagRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FraudFlag> getHighRiskFlags() {
        return fraudFlagRepository.findByRiskLevel(FraudRiskLevel.HIGH);
    }

    private FraudFlag createFlag(Long userId, Long transactionId, Long accountId,
                                  FraudRiskLevel riskLevel, String reason,
                                  BigDecimal amount, String ipAddress, String deviceInfo) {
        FraudFlag flag = new FraudFlag();
        flag.setUserId(userId);
        flag.setTransactionId(transactionId);
        flag.setAccountId(accountId);
        flag.setRiskLevel(riskLevel);
        flag.setReason(reason);
        flag.setFlaggedAmount(amount);
        flag.setIpAddress(ipAddress);
        flag.setDeviceInfo(deviceInfo);

        FraudFlag saved = fraudFlagRepository.save(flag);
        log.warn("FRAUD FLAG: level={}, reason={}, userId={}, amount={}", riskLevel, reason, userId, amount);
        return saved;
    }
}
