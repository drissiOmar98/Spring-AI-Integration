package com.omar.authserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springaicommunity.mcp.security.authorizationserver.config.McpAuthorizationServerConfigurer.mcpAuthorizationServer;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration for the Authorization Server.
 *
 * <p>
 * This class configures:
 * <ul>
 *     <li>OAuth2 Authorization Server endpoints</li>
 *     <li>Federated login via GitHub (OAuth2 Login)</li>
 *     <li>Global authentication rules</li>
 *     <li>CORS policy for cross-origin access</li>
 * </ul>
 * </p>
 *
 * <p>
 * It leverages Spring Security along with MCP Authorization Server integration
 * to provide a secure identity provider for MCP clients.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Main security filter chain for the Authorization Server.
     *
     * <p>
     * Configures:
     * <ul>
     *     <li>Authentication required for all endpoints</li>
     *     <li>MCP Authorization Server (OAuth2 endpoints)</li>
     *     <li>OAuth2 login with external provider (GitHub)</li>
     *     <li>CORS support</li>
     * </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} configuration object
     * @return configured {@link SecurityFilterChain}
     * @throws Exception in case of configuration errors
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Require authentication for all incoming requests
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )

                // Enable MCP Authorization Server (OAuth2 endpoints like /oauth2/token, /authorize)
                .with(mcpAuthorizationServer(), withDefaults())

                // Enable CORS with custom configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Enable OAuth2 login (GitHub as Identity Provider)
                .oauth2Login(withDefaults());

        return http.build();
    }

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
