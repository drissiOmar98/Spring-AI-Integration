package com.omar.spring_ai_04_chat_with_ollama.functions.model;

/**
 * ⛅ Condition DTO - Weather condition details
 *
 * @param text Human-readable weather description (e.g., "Sunny", "Partly Cloudy")
 */
public record Condition(String text) {
}