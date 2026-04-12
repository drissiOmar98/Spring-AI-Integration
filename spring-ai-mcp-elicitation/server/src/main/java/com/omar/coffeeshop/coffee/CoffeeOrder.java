package com.omar.coffeeshop.coffee;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CoffeeOrder(
        @JsonPropertyDescription("Coffee size: small, medium, or large")
        String size,
        @JsonPropertyDescription("Coffee type: espresso, latte, cappuccino, americano, or drip")
        String type,
        @JsonPropertyDescription("Milk preference: none, whole, skim, oat, almond, or soy")
        String milk
) {}
