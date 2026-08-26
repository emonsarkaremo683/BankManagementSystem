package com.elitetech_inc.ensarkbank.auth_management.auth.security;

import com.elitetech_inc.ensarkbank.common.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey; // Changed from java.security.Key
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class JwtUtil {

    /** HS256 requires a key of at least 256 bits. */
    private static final int MIN_SECRET_BYTES = 32;

    /** Minimum number of distinct characters — a cheap stand-in for entropy. */
    private static final int MIN_DISTINCT_CHARS = 12;

    /** Fragments that mark the value as a placeholder rather than a real secret. */
    private static final List<String> PLACEHOLDER_MARKERS = List.of(
            "changeme", "change-me", "change_me",
            "placeholder", "example", "sample", "dummy", "todo",
            "your-secret", "yoursecret", "your_secret",
            "set jwt_secret", "jwt_secret", "environment variable",
            "secretkey", "mysecret", "supersecret", "test-secret"
    );

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey signingKey;

    /**
     * Fails the application context at startup when jwt.secret is missing, too
     * short for HS256, or looks like a placeholder. Spring does not support the
     * bash-style {@code ${VAR:?message}} required syntax, so this is where the
     * "required" contract is actually enforced.
     */
    @PostConstruct
    void validateSecret() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                    "jwt.secret is not set. Provide the JWT_SECRET environment variable "
                            + "with at least " + MIN_SECRET_BYTES + " random bytes.");
        }

        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < MIN_SECRET_BYTES) {
            throw new IllegalStateException(
                    "jwt.secret is too short (" + bytes.length + " bytes). HS256 requires at least "
                            + MIN_SECRET_BYTES + " bytes. Set a longer JWT_SECRET.");
        }

        String normalized = secret.toLowerCase(Locale.ROOT);
        for (String marker : PLACEHOLDER_MARKERS) {
            if (normalized.contains(marker)) {
                throw new IllegalStateException(
                        "jwt.secret looks like a placeholder (contains \"" + marker + "\"). "
                                + "Set JWT_SECRET to a real random value.");
            }
        }

        long distinct = secret.chars().distinct().count();
        if (distinct < MIN_DISTINCT_CHARS) {
            throw new IllegalStateException(
                    "jwt.secret has too little variation (" + distinct + " distinct characters, "
                            + "at least " + MIN_DISTINCT_CHARS + " required). Set JWT_SECRET to a real random value.");
        }

        this.signingKey = Keys.hmacShaKeyFor(bytes);
    }

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.emp-expiration}")
    private long empExpiration;

    @Value("${jwt.verification-expiration}")
    private long verificationExpiration;


    @Value("${jwt.reset-expiration}")
    private long resetExpiration;


    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public String generateCustomerToken(String email){
        return Jwts.builder()
                .subject(email)                         // Replaces setSubject()
                .claim("role", Role.CUSTOMER)            // Converts Enum to String for clean JSON serialization
                .issuedAt(new Date())  // Replaces setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Replaces setExpiration()
                .signWith(getKey())                     // Replaces signWith(key, algorithm)
                .compact();
    }

    public String generateEmployeeToken(String email, Role role){
        return Jwts.builder()
                .subject(email)                         // Replaces setSubject()
                .claim("role", role.name())            // Converts Enum to String for clean JSON serialization
                .issuedAt(new Date())  // Replaces setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + empExpiration)) // Replaces setExpiration()
                .signWith(getKey())                     // Replaces signWith(key, algorithm)
                .compact();
    }

    public String generateRefreshToken(String email, Role role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role.name())
                .claim("purpose", "REFRESH")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getKey())
                .compact();
    }

    // ── Password reset token (short-lived, single purpose) ─────────
    public String generateResetToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("purpose", "PASSWORD_RESET")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + resetExpiration))
                .signWith(getKey())
                .compact();
    }

    // ── Email verification token (short-lived, single purpose) ────
    public String generateVerificationToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("purpose", "EMAIL_VERIFICATION")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + verificationExpiration))
                .signWith(getKey())
                .compact();
    }



    // get Email
    public String getEmail(String token){
        return (String) getClaims(token).getSubject();
    }

    // Get Role
    public Role getRole(String token){
        return Role.valueOf((String) getClaims(token).get("role"));
    }

    // Validate token — checks signature + expiry
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public String extractPurpose(String token) {
        return (String) getClaims(token).get("purpose");
    }

    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(extractPurpose(token));
    }


    // Validates token AND checks it was issued for the expected purpose
    public boolean isValidForPurpose(String token, String expectedPurpose) {
        try {
            Claims claims = getClaims(token);
            return expectedPurpose.equals(claims.get("purpose"));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


// old code
//    private Claims  getClaims(String token){
//        return Jwts.parser()
//                .setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }

    // updated code

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey(){
        return signingKey;
    }
}
