package com.omar.spring_ai_04_chat_with_ollama.functions.model;

/**
 * ☀️ Response DTO - Complete weather API response
 *
 * @param location Geographic information about the requested city
 * @param current  Current weather conditions at the location
 */
public record Response(Location location, Current current) {
}