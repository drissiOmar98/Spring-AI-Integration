package com.omar.spring_ai_04_chat_with_ollama.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * 🔧 Function Configuration - Spring AI Function Calling Setup
 * <p>
 * Configuration class that registers custom functions for AI tool calling.
 * Maps Java functions to AI-callable tools using Spring AI's function calling framework.
 * <p>
 * Key Concepts:
 * - @Bean methods expose functions as AI-callable tools
 * - @Description provides natural language documentation for AI models
 * - Function interface enables AI to call external services
 * - Configuration pattern separates bean creation from business logic
 */
@Configuration
public class FunctionConfiguration {

    private final WeatherConfigProperties props;

    /**
     * Constructor for dependency injection of weather configuration
     *
     * @param props Weather configuration properties from application.yml
     */
    public FunctionConfiguration(WeatherConfigProperties props) {
        this.props = props;
    }


    /**
     * Registers weather service as an AI-callable function
     * <p>
     * This function bean enables AI models to:
     * 1. Detect when weather information is needed in conversation
     * 2. Extract city parameter from natural language
     * 3. Call this function with the extracted city
     * 4. Use weather data in subsequent responses
     * <p>
     * Example AI Interaction:
     * User: "What's the weather like in Paris?"
     * AI: [Detects need for weather → Calls currentWeatherFunction("Paris")]
     * AI: "In Paris, it's currently 68°F with clear skies and 10 mph winds."
     *
     * @return Function bean that AI can call for current weather conditions
     * @apiNote Function Signature Design:
     * - Input: WeatherService.Request (city name)
     * - Output: WeatherService.Response (structured weather data)
     * - Type safety: Compile-time validation of inputs/outputs
     * - Documentation: @Description helps AI understand function purpose
     */
    @Bean
    @Description("Get the current weather conditions for the given city.")
    public Function<WeatherService.Request, WeatherService.Response> currentWeatherFunction() {
        return new WeatherService(props);
    }

}