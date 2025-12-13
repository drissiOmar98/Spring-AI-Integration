package com.omar.spring_ai_04_chat_with_ollama.functions.model;

/**
 * 🌡️ Current DTO - Current weather conditions
 *
 * @param temp_f    Temperature in Fahrenheit
 * @param condition Weather condition description
 * @param wind_mph  Wind speed in miles per hour
 * @param humidity  Humidity percentage
 */
public record Current(String temp_f, Condition condition, String wind_mph, String humidity) {
}