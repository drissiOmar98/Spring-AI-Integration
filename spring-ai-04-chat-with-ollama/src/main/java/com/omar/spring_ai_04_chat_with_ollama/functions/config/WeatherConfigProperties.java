package com.omar.spring_ai_04_chat_with_ollama.functions.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 🌤️ Weather Configuration Properties
 * <p>
 * Spring Boot configuration properties record for external weather API configuration.
 * Maps properties from application.yml/application.properties to immutable Java record.
 * <p>
 * Usage in application.yml:
 *   weather:
 *     api-key: ${WEATHER_API_KEY}
 *     api-url: https://api.weatherapi.com/v1
 *
 * @param apiKey API key for authenticating with the weather service
 * @param apiUrl Base URL for the weather API endpoint
 */
@ConfigurationProperties(value = "weather")
public record WeatherConfigProperties(String apiKey, String apiUrl) {

}