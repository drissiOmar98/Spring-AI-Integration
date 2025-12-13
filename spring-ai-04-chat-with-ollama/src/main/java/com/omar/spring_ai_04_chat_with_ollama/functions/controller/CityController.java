package com.omar.spring_ai_04_chat_with_ollama.functions.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 🏙️ City Controller - AI-Powered City Information with Weather Integration
 * <p>
 * REST controller that provides AI-driven city information with automatic weather lookup.
 * Demonstrates Spring AI function calling with pre-configured tools for enhanced responses.
 * <p>
 * Architecture: Natural Query → AI Analysis → Function Detection → Weather API → Enhanced Response
 * <p>
 * Endpoint: GET /cities
 * <p>
 * Key Features:
 * - Pre-configured AI system prompt for city-related expertise
 * - Automatic weather function calling when needed
 * - Natural language understanding of city queries
 * - Context-aware responses with real-time data
 */
@RestController
public class CityController {

    private final ChatClient chatClient;

    /**
     * Constructs city controller with AI assistant specialized for city information
     *
     * @param builder ChatClient builder for creating AI chat instances
     *
     * @apiNote Configuration includes:
     *          - Default system prompt: Sets AI's role and expertise
     *          - Default tools: Registers weather function for automatic calling
     *          - Extensible: Additional tools can be added for more capabilities
     */
    public CityController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("You are a helpful AI Assistant answering questions about cities around the world.")
                .defaultTools("currentWeatherFunction")
                .build();
    }

    /**
     * AI-Powered City Information Endpoint with Weather Integration
     * <p>
     * Handles natural language queries about cities and automatically fetches
     * weather data when relevant. The AI detects when weather information
     * would enhance the response and calls the registered weather function.
     * <p>
     * Example Interactions:
     * <p>
     * Query: "What's the population of Tokyo?"
     * Response: "Tokyo has a population of approximately 37 million people..."
     * <p>
     * Query: "How's the weather in Dubai today?"
     * AI Action: Calls currentWeatherFunction("Dubai")
     * Response: "In Dubai, it's currently 95°F with sunny conditions..."
     * <p>
     * Query: "Compare the climates of London and Sydney"
     * AI Action: May call weather function for both cities
     * Response: "London has a temperate maritime climate (currently 55°F)..."
     *
     * @param message Natural language query about cities
     *                Examples:
     *                - "Tell me about Paris"
     *                - "What's the weather like in New York?"
     *                - "Compare Berlin and Madrid"
     *                - "Best time to visit Bangkok"
     * @return AI-generated response enhanced with weather data when applicable
     *
     * @apiNote Function Calling Process:
     *          1. User asks city-related question
     *          2. AI analyzes if weather data would improve response
     *          3. If yes, AI calls currentWeatherFunction with extracted city
     *          4. Weather API returns structured data
     *          5. AI incorporates weather into natural language response
     */
    @GetMapping("/cities")
    public String cityFaq(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}