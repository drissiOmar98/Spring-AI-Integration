package com.omar.spring_ai_04_chat_with_ollama.functions.service;

import com.omar.spring_ai_04_chat_with_ollama.functions.config.WeatherConfigProperties;
import com.omar.spring_ai_04_chat_with_ollama.functions.model.Request;
import com.omar.spring_ai_04_chat_with_ollama.functions.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.util.function.Function;


/**
 * 🌡️ Weather Service - External API Integration via Function Calling
 * <p>
 * Implements a Java Function that retrieves current weather data from WeatherAPI.com.
 * This service demonstrates:
 * - External REST API integration
 * - Spring AI function calling pattern
 * - Structured error handling and logging
 * <p>
 * API Documentation: https://www.weatherapi.com/api-explorer.aspx
 *
 * @implNote Function Pattern: Implements Function<T,R> interface for AI tool calling
 * @implSpec Weather API returns data in JSON format mapped to nested records
 */
public class WeatherService implements Function<Request, Response> {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    /**
     * Spring RestClient for making HTTP requests to external weather API
     */
    private final RestClient restClient;

    /**
     * Configuration properties for weather API (URL, API key)
     */
    private final WeatherConfigProperties weatherProps;

    /**
     * Constructs weather service with configuration and initializes REST client
     *
     * @param props Weather configuration properties (injected via constructor)
     */
    public WeatherService(WeatherConfigProperties props) {
        this.weatherProps = props;
        log.info("Weather API URL: {}", weatherProps.apiUrl());
        log.info("Weather API Key: {}", weatherProps.apiKey());
        this.restClient = RestClient.create(weatherProps.apiUrl());
    }

    /**
     * Main function that retrieves current weather for a given city
     *
     * @param weatherRequest Contains the city name to get weather for
     * @return Structured weather response with location and current conditions
     *
     * @implNote API Call Details:
     *           - Uses GET /current.json endpoint
     *           - Requires API key as query parameter
     *           - Returns JSON response mapped to Response record
     *           - Includes error handling via RestClient
     */
    @Override
    public Response apply(Request weatherRequest) {
        log.info("Weather Request: {}",weatherRequest);
        Response response = restClient.get()
                .uri("/current.json?key={key}&q={q}", weatherProps.apiKey(), weatherRequest.city())
                .retrieve()
                .body(Response.class);
        log.info("Weather API Response: {}", response);
        return response;
    }
}