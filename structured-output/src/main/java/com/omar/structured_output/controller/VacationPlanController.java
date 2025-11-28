package com.omar.structured_output.controller;

import com.omar.structured_output.model.Itinerary;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 🔹 VacationPlanController
 * <p>
 * This controller demonstrates the usage of **Spring AI** to generate AI-driven vacation plans.
 * It provides two types of endpoints:
 * <p>
 * 1. **Unstructured Output** (`/vacation/unstructured`):
 *    - Returns raw text from the AI model.
 *    - Useful for quick AI suggestions where a simple string response suffices.
 * <p>
 * 2. **Structured Output** (`/vacation/structured`):
 *    - Leverages Spring AI's ability to map AI responses directly to Java records (`Itinerary` and `Activity`).
 *    - Returns structured JSON suitable for frontend rendering, calendar integration, or programmatic processing.
 *    - Demonstrates how AI responses can be typed, validated, and consumed as standard Spring Boot objects.
 * <p>
 * This controller highlights the power of **structured outputs** in Spring AI, showing how AI-generated content can
 * seamlessly integrate into strongly-typed Java applications, making it easier to work with, validate, and display.
 */
@RestController
public class VacationPlanController {

    private final ChatClient chatClient;

    public VacationPlanController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 🔹 Unstructured Vacation Plan Endpoint
     * Endpoint: GET /vacation/unstructured
     *
     * Sends a fixed user prompt to the AI model asking for a vacation plan in Montreal for 4 days.
     * Returns a raw, unstructured text response from the AI.
     *
     * Use Case:
     * - Quickly get AI suggestions in plain text format.
     * - Ideal for testing, casual queries, or scenarios where structured output is not required.
     *
     * @return A string containing AI-generated vacation recommendations
     */
    @GetMapping("/vacation/unstructured")
    public String vacationUnstructured() {
        return chatClient.prompt()
                .user("What's a good vacation plan while I'm in Montreal CA for 4 days?")
                .call()
                .content();
    }


    /**
     * 🔹 Structured Vacation Plan Endpoint
     * Endpoint: GET /vacation/structured
     * <p>
     * Sends a user prompt to the AI asking for a vacation plan for the specified destination and duration (3 days by default).
     * The AI response is automatically mapped to an `Itinerary` object containing a list of `Activity` records.
     * Each activity includes fields: activity name, location, day, and time.
     * <p>
     * Use Case:
     * - Provides structured JSON suitable for front-end rendering, calendar integration, or further processing.
     * <p>
     * Query Parameter:
     * - destination (optional): The destination city. Defaults to "Cleveland, OH".
     *
     * @param destination The target destination for the vacation plan
     * @return An Itinerary object containing a structured list of activities
     */
    @GetMapping("/vacation/structured")
    public Itinerary vacationStructured(@RequestParam(value = "destination", defaultValue = "Cleveland, OH") String destination) {
        return chatClient.prompt()
                .user(u -> {
                    // Build a dynamic user prompt with the destination parameter
                    u.text("What's a good vacation plan while I'm in {destination} for 3 days?");
                    u.param("destination", destination);
                })
                .call()
                .entity(Itinerary.class);
    }

}