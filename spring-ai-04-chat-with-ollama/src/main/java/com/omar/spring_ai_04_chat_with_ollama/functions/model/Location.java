package com.omar.spring_ai_04_chat_with_ollama.functions.model;

/**
 * 📍 Location DTO - Geographic details
 *
 * @param name    City name (e.g., "London")
 * @param region  Region/state (e.g., "England")
 * @param country Country (e.g., "United Kingdom")
 * @param lat     Latitude coordinate
 * @param lon     Longitude coordinate
 */
public record Location(String name, String region, String country, Long lat, Long lon) {
}