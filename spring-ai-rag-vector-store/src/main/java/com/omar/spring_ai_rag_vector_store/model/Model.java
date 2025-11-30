package com.omar.spring_ai_rag_vector_store.model;

/**
 * Represents a single AI model entry used in the RAG vector store system.
 * <p>
 * Each model contains:
 * - The company providing the model (e.g., OpenAI, Anthropic, Cohere)
 * - The model name/version
 * - The supported context window size in tokens
 * <p>
 * This record is typically returned as part of a metadata endpoint
 * or used internally for model capability inspection.
 */
public record Model(String company, String model, int contextWindowSize) {
}