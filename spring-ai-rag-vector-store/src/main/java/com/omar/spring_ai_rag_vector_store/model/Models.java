package com.omar.spring_ai_rag_vector_store.model;

import java.util.List;

/**
 * Wrapper record representing a collection of available AI models.
 * <p>
 * Used to expose a structured JSON response when returning multiple model
 * definitions from a REST API endpoint (e.g., GET /models).
 */
public record Models(List<Model> models) {
}