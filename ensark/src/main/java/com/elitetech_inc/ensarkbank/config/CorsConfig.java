package com.elitetech_inc.ensarkbank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${image.upload.dir}")
    private String uploadDir;

    /**
     * Allowed browser origin, sourced from the FRONTEND_URL environment variable
     * (see app.frontend-url in application.properties).
     */
    @Value("${app.frontend-url}")
    private String frontendUrl;

    /**
     * Always-allowed dev origin. The running backend may be configured with a
     * deployed FRONTEND_URL while the UI is still served from localhost:4200,
     * which would otherwise CORS-block every API call and reject WebSocket
     * handshakes from the local dev server.
     */
    private static final String DEV_FRONTEND_URL = "http://localhost:4200";

    /**
     * The single CORS definition for the whole application. Spring Security applies
     * it to every request that passes through the security filter chain, which
     * includes the statically served /uploads/** resources — so uploads and API
     * responses always advertise the same origin.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOrigins = new java.util.ArrayList<>();
        allowedOrigins.add(frontendUrl);
        if (!DEV_FRONTEND_URL.equals(frontendUrl)) {
            allowedOrigins.add(DEV_FRONTEND_URL);
        }
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Serves uploaded files from the configured upload directory. CORS for these
     * URLs comes from {@link #corsConfigurationSource()}, not from a separate
     * WebMvc CORS mapping.
     */
    @Bean
    public WebMvcConfigurer uploadResourceConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                String absolutePath = new File(uploadDir).getAbsolutePath() + File.separator;
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:" + absolutePath);
            }
        };
    }
}
