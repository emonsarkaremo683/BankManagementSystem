package com.elitetech_inc.ensarkbank.config;

import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfig {

    private final ConcurrentHashMap<String, LoginAttempt> attempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String key) {
        LoginAttempt attempt = attempts.get(key);
        if (attempt == null) return false;
        if (attempt.isExpired()) {
            attempts.remove(key);
            return false;
        }
        return attempt.getCount() >= 5;
    }

    public void recordAttempt(String key) {
        attempts.compute(key, (k, v) -> {
            if (v == null || v.isExpired()) return new LoginAttempt();
            v.increment();
            return v;
        });
    }

    public void resetAttempts(String key) {
        attempts.remove(key);
    }

    private static class LoginAttempt {
        private int count = 0;
        private final long createdAt = System.currentTimeMillis();

        void increment() { count++; }
        int getCount() { return count; }
        boolean isExpired() { return System.currentTimeMillis() - createdAt > 900_000; }
    }
}
