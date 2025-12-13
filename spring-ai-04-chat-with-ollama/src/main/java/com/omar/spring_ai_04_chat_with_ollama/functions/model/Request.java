package com.omar.spring_ai_04_chat_with_ollama.functions.model;

/**
 * 🌍 Request DTO - Input for weather function
 *
 * @param city City name to get weather for (e.g., "London", "New York")
 */
public record Request(String city) {
}