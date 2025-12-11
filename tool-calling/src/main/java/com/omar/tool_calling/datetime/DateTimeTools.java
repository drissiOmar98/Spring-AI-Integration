package com.omar.tool_calling.datetime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

/**
 * ⏰ DateTime Tools - Spring AI Tool Calling Example
 * <p>
 * Demonstrates the use of Spring AI's tool calling functionality.
 * This class defines executable tools that AI models can invoke during conversations.
 * <p>
 * Features:
 * - @Tool annotation marks methods as callable by AI models
 * - Automatic parameter extraction from natural language
 * - Structured function calling for external API integration
 * - Timezone-aware date/time operations
 * <p>
 * Tool calling enables AI models to:
 * 1. Recognize when a tool is needed
 * 2. Extract parameters from natural language
 * 3. Execute the tool method
 * 4. Use results in subsequent responses
 */
public class DateTimeTools {


    /**
     * Retrieves the current date and time adjusted for the user's timezone.
     * <p>
     * This tool method is automatically recognized by Spring AI and can be invoked
     * by AI models when users ask about current time or date. The AI will:
     * 1. Detect the need for current date/time information
     * 2. Call this method without requiring explicit parameters
     * 3. Incorporate the result into its response
     * <p>
     * Example AI interaction:
     * User: "What's the current time?"
     * AI: [Internally calls getCurrentDateTime() → returns "2024-01-15T14:30:00Z[UTC]"]
     * AI: "The current time is 2:30 PM UTC on January 15, 2024."
     *
     * @return ISO-8601 formatted date-time string in the user's timezone
     *
     * @apiNote This tool demonstrates:
     *          - Zero-parameter tool invocation
     *          - Automatic timezone handling via LocaleContextHolder
     *          - Simple data retrieval pattern
     */
    @Tool(description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

}