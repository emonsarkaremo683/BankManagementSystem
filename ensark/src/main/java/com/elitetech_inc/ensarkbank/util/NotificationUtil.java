package com.elitetech_inc.ensarkbank.util;

import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import com.elitetech_inc.ensarkbank.auth_management.user.repository.UserRepository;
import com.elitetech_inc.ensarkbank.common.enums.NotificationType;
import com.elitetech_inc.ensarkbank.common.enums.Role;
import com.elitetech_inc.ensarkbank.common.notification.websocket.WebSocketNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationUtil {

    private final WebSocketNotificationService webSocketNotificationService;
    private final UserRepository userRepository;

    public void notifyAuthorities(NotificationType type, String title, String message,
                                  String referenceId, String referenceType) {
        List<User> authorities = userRepository.findByRoleNot(Role.CUSTOMER);
        for (User authority : authorities) {
            webSocketNotificationService.sendNotificationToUser(
                    authority.getId(), type, title, message, referenceId, referenceType);
        }
        log.info("Authority notification sent: {} - {}", title, message);
    }

    public void notifyUser(Long userId, NotificationType type, String title, String message,
                            String referenceId, String referenceType) {
        webSocketNotificationService.sendNotificationToUser(
                userId, type, title, message, referenceId, referenceType);
        log.info("User notification sent to user {}: {}", userId, title);
    }
}
