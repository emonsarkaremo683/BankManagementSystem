package com.elitetech_inc.ensarkbank.common.notification.websocket;

import com.elitetech_inc.ensarkbank.auth_management.user.entity.User;
import java.security.Principal;

public class WebSocketUserPrincipal implements Principal {

    private final User user;

    public WebSocketUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getId().toString();
    }

    public User getUser() {
        return user;
    }
}
