package com.elitetech_inc.ensarkbank.common.notification.websocket;

import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.notification.dto.NotificationResponse;
import com.elitetech_inc.ensarkbank.common.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Async
    public void sendNotificationToUser(Long userId, NotificationType type,
                                        String title, String message,
                                        String referenceId, String referenceType) {
        try {
            NotificationResponse notification = notificationService.createNotification(
                    userId, type, title, message, referenceId, referenceType);

            messagingTemplate.convertAndSendToUser(
                    userId.toString(),
                    "/queue/notifications",
                    notification
            );

            log.info("WebSocket notification sent to user {}: {}", userId, title);
        } catch (Exception e) {
            log.error("Failed to send WebSocket notification to user {}: {}", userId, e.getMessage());
        }
    }

    @Async
    public void broadcastNotification(NotificationType type, String title, String message, String referenceId, String referenceType) {
        try {
            NotificationResponse persisted = notificationService.createNotification(
                    null, type, title, message, referenceId, referenceType);

            messagingTemplate.convertAndSend("/topic/notifications", persisted);
            log.info("Broadcast notification successfully sent: {}", title);
        } catch (Exception e) {
            log.error("Failed to broadcast notification '{}': {}", title, e.getMessage(), e);
        }
    }

}
