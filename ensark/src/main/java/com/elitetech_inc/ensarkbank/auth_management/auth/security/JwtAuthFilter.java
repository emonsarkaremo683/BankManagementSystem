package com.elitetech_inc.ensarkbank.auth_management.auth.security;

import com.elitetech_inc.ensarkbank.auth_management.auth.repository.TokenBlacklistRepository;
import com.elitetech_inc.ensarkbank.auth_management.auth.serviceimpl.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * Utility class used for JWT operations
     * such as validation and extracting claims.
     */
    private final JwtUtil jwtUtil;

    /**
     * Custom UserDetailsService implementation
     * used to load user information from the database.
     */
    private final CustomUserDetails userDetailsService;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    /**
     * This method executes for every request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ============================================================
        // STEP 1: Read the Authorization header
        // Example:
        // Authorization: Bearer eyJhbGciOiJIUzI1NiJ9....
        //
        // The Authorization header is the ONLY accepted carrier. A ?token=
        // query-parameter fallback used to exist here; it leaked tokens into
        // access logs, browser history and Referer headers, so it is gone.
        // (The WebSocket handshake has its own query-parameter handling in
        // JwtHandshakeInterceptor.)
        // ============================================================
        String token = null;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (tokenBlacklistRepository.existsByToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtil.isTokenValid(token) && isAccessToken(token)) {

            // ========================================================
            // STEP 5: Extract user email from JWT payload
            // ========================================================
            String email = jwtUtil.getEmail(token);

            // ========================================================
            // STEP 6: Authenticate user only if:
            // 1. Email exists in token
            // 2. User is not already authenticated
            // ========================================================
            if (
                    email != null &&
                            SecurityContextHolder.getContext()
                                    .getAuthentication() == null
            ) {

                // ====================================================
                // STEP 7: Load user details from database
                // using email extracted from token.
                // ====================================================
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                // ====================================================
                // STEP 8: Create Authentication object
                //
                // Principal   -> UserDetails
                // Credentials -> null (password not needed)
                // Authorities -> Roles/Permissions
                // ====================================================
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // ====================================================
                // STEP 9: Attach request-specific details
                //
                // Includes:
                // - Remote IP Address
                // - Session ID (if available)
                // ====================================================
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // ====================================================
                // STEP 10: Store authentication object in
                // Spring Security Context.
                //
                // After this step:
                // SecurityContextHolder.getContext()
                //         .getAuthentication()
                //
                // will return the authenticated user.
                // ====================================================
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        // ============================================================
        // STEP 11: Continue filter chain
        //
        // Request moves to next filter or controller.
        // ============================================================
        filterChain.doFilter(request, response);

    }

    /**
     * Only normal access tokens may authenticate a request.
     * <p>
     * Single-purpose tokens (REFRESH, PASSWORD_RESET, EMAIL_VERIFICATION) carry a
     * "purpose" claim and are redeemable only at their dedicated endpoints. Any
     * token with a non-null purpose claim is rejected here, so a leaked reset or
     * verification link can never be replayed as a session credential.
     */
    private boolean isAccessToken(String token) {
        try {
            return jwtUtil.extractPurpose(token) == null;
        } catch (RuntimeException e) {
            // Unparseable token — treat as not authenticating.
            return false;
        }
    }
}
