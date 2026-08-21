package com.ensark.ensarkbank.util;

import java.util.Locale;

public final class EnumLabels {

    private EnumLabels() {}

    /** "FIXED_DEPOSIT" -> "Fixed Deposit" */
    public static String pretty(String enumName) {
        if (enumName == null) return "";
        String[] parts = enumName.toLowerCase(Locale.US).replace("_", " ").trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
        }
        return sb.toString();
    }

    /** "Fixed Deposit" -> "FIXED_DEPOSIT" */
    public static String toEnumName(String label) {
        if (label == null) return "";
        return label.trim().toUpperCase(Locale.US).replace(" ", "_");
    }
}
