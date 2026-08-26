package com.elitetech_inc.ensarkbank.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reusable, dependency-free validation primitives used across the project's
 * validator classes. Every method throws {@link IllegalArgumentException} with a
 * descriptive message when the check fails, so callers get a consistent,
 * user-friendly error contract.
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }

    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE =
            Pattern.compile("^(\\+?\\d{10,15})$");

    public static void notNull(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    public static void notBlank(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    public static void maxLength(String value, int max, String field) {
        if (value != null && value.length() > max) {
            throw new IllegalArgumentException(field + " must be at most " + max + " characters");
        }
    }

    public static void validEmail(String value, String field) {
        notBlank(value, field);

        if (!EMAIL.matcher(value.trim()).matches()) {
            throw new IllegalArgumentException(field + " must be a valid email address");
        }
    }

    public static void validPhone(String value, String field) {
        notBlank(value, field);
        String cleaned = value.trim().replaceAll("[\\s-]", "");
        if (!PHONE.matcher(cleaned).matches()) {
            throw new IllegalArgumentException(field + " must be a valid phone number (10-15 digits)");
        }
    }

    public static void positive(BigDecimal value, String field) {
        notNull(value, field);
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(field + " must be greater than zero");
        }
    }

    public static void nonNegative(BigDecimal value, String field) {
        if (value != null && value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(field + " must be non-negative");
        }
    }

    public static void positiveOrZero(double value, String field) {
        if (value < 0) {
            throw new IllegalArgumentException(field + " must be non-negative");
        }
    }

    public static void positiveLong(Long value, String field) {
        notNull(value, field);
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be a positive identifier");
        }
    }

    public static void notFuture(Date date, String field) {
        if (date != null && date.after(new Date())) {
            throw new IllegalArgumentException(field + " cannot be in the future");
        }
    }

//    public static void notFuture(LocalDateTime dateTime, String field) {
//        if (dateTime != null && dateTime.isAfter(LocalDateTime.now())) {
//            throw new IllegalArgumentException(field + " cannot be in the future");
//        }
//    }

    public static void inRange(int value, int min, int max, String field) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(field + " must be between " + min + " and " + max);
        }
    }

    public static <T extends Enum<T>> void validEnum(T value, Class<T> enumClass, String field) {
        notNull(value, field);
        try {
            Enum.valueOf(enumClass, value.name());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(field + " has an invalid value: " + value);
        }
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
