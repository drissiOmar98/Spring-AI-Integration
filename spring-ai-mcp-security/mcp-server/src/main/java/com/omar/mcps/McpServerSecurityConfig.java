package com.omar.mcps;

import org.springaicommunity.mcp.security.server.config.McpServerOAuth2Configurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration for the MCP Server.
 *
 * <p>
 * This class configures authentication and authorization using Spring Security
 * and integrates MCP-specific OAuth2 security via {@code McpServerOAuth2Configurer}.
 * </p>
 *
 * <p>
 * Key responsibilities:
 * <ul>
 *     <li>Secure all endpoints using OAuth2 JWT authentication</li>
 *     <li>Integrate with an external Authorization Server</li>
 *     <li>Enable method-level security (e.g., @PreAuthorize)</li>
 *     <li>Configure CORS for cross-origin requests</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class McpServerSecurityConfig {

    /**
     * Authorization Server issuer URL.
     *
     * <p>
     * This is used to validate incoming JWT tokens and ensure they are issued
     * by the trusted authorization server.
     * </p>
     */
    @Value("${authorization.server.url}")
    private String authServerUrl;

    /**
     * Main security filter chain configuration.
     *
     * <p>
     * Configures:
     * <ul>
     *     <li>Authentication requirement for all endpoints</li>
     *     <li>CORS policy</li>
     *     <li>CSRF disabled (stateless API)</li>
     *     <li>MCP OAuth2 integration</li>
     * </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} configuration object
     * @return configured {@link SecurityFilterChain}
     * @throws Exception in case of configuration errors
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .with(
                        McpServerOAuth2Configurer.mcpServerOAuth2(),
                        (mcpAuthorization) -> {
                            // REQUIRED: the authserver's issuer URI
                            mcpAuthorization.authorizationServer(this.authServerUrl);
                            // OPTIONAL: enforce the `aud` claim in the JWT token.
                            mcpAuthorization.validateAudienceClaim(true);
                        }
                )
                .build();
    }

    /**
     * CORS configuration for the MCP server.
     *
     * <p>
     * Allows cross-origin requests. Current configuration is permissive
     * (for development purposes).
     * </p>
     *
     * ⚠️ In production:
     * <ul>
     *     <li>Restrict allowed origins</li>
     *     <li>Avoid using "*" wildcard</li>
     * </ul>
     *
     * @return configured {@link CorsConfigurationSource}
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all origins for development (use specific origins in production)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}
