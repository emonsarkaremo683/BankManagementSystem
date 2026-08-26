package com.elitetech_inc.ensarkbank.common.notification.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardFeedService {

    private final SimpMessagingTemplate messagingTemplate;

    @Async
    public void pushTransactionUpdate(List<Long> userIds, Long transactionId, String referenceNo,
                                      String transactionType, BigDecimal amount, boolean isCredit,
                                      BigDecimal balanceAfter, String accountNumber,
                                      String remarks, String channel) {
        try {
            if (userIds == null || userIds.isEmpty()) return;

            for (Long userId : userIds) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("type", "TRANSACTION");
                payload.put("transactionId", transactionId);
                payload.put("referenceNo", referenceNo);
                payload.put("transactionType", transactionType);
                payload.put("amount", amount);
                payload.put("isCredit", isCredit);
                payload.put("balanceAfter", balanceAfter);
                payload.put("accountNumber", accountNumber);
                payload.put("remarks", remarks);
                payload.put("channel", channel);
                payload.put("timestamp", LocalDateTime.now().toString());

                messagingTemplate.convertAndSendToUser(
                        userId.toString(),
                        "/queue/dashboard",
                        payload
                );
            }
        } catch (Exception e) {
            log.error("Failed to push dashboard update for account {}: {}", accountNumber, e.getMessage());
        }
    }

    @Async
    public void pushBalanceUpdate(List<Long> userIds, String accountNumber,
                                  BigDecimal availableBalance, BigDecimal currentBalance,
                                  BigDecimal holdBalance) {
        try {
            if (userIds == null || userIds.isEmpty()) return;

            for (Long userId : userIds) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("type", "BALANCE_UPDATE");
                payload.put("accountNumber", accountNumber);
                payload.put("availableBalance", availableBalance);
                payload.put("currentBalance", currentBalance);
                payload.put("holdBalance", holdBalance);
                payload.put("timestamp", LocalDateTime.now().toString());

                messagingTemplate.convertAndSendToUser(
                        userId.toString(),
                        "/queue/dashboard",
                        payload
                );
            }
        } catch (Exception e) {
            log.error("Failed to push balance update for account {}: {}", accountNumber, e.getMessage());
        }
    }

    @Async
    public void pushAlertToUser(Long userId, String alertType, String title, String message) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("type", "ALERT");
            payload.put("alertType", alertType);
            payload.put("title", title);
            payload.put("message", message);
            payload.put("timestamp", LocalDateTime.now().toString());

            messagingTemplate.convertAndSendToUser(
                    userId.toString(),
                    "/queue/dashboard",
                    payload
            );
        } catch (Exception e) {
            log.error("Failed to push alert to user {}: {}", userId, e.getMessage());
        }
    }
}
