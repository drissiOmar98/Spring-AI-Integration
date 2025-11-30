package com.omar.spring_ai_rag_vector_store.controller;

import com.omar.spring_ai_rag_vector_store.model.Models;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 🤖 RAG Models Controller
 * <p>
 * Provides a retrieval-augmented generation (RAG) endpoint for querying AI model information.
 * Enhances responses with relevant context retrieved from the vector store for accurate,
 * context-aware answers about AI models and their specifications.
 * <p>
 * Endpoint: GET /rag/models
 * <p>
 * Key Features:
 * - Retrieval-Augmented Generation (RAG) for enhanced accuracy
 * - Vector store integration for semantic search
 * - Structured JSON response with model information
 * - Default query for comprehensive model listing
 * <p>
 * Use Cases:
 * - Querying AI model specifications and capabilities
 * - Getting context window information for different models
 * - Comparing model features and limitations
 * - Technical documentation retrieval
 */
@RestController
public class ModelsController {

    /**
     * Spring AI ChatClient configured with RAG capabilities.
     * Enhanced with QuestionAnswerAdvisor for retrieval-augmented generation
     * from the vector store, providing contextually relevant responses.
     */
    private final ChatClient chatClient;


    /**
     * Constructor that builds a RAG-enabled ChatClient.
     *
     * @param builder ChatClient builder for creating configured chat instances
     * @param vectorStore Vector store containing embedded model documentation
     *                   for retrieval-augmented generation
     */
    public ModelsController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                // Enhance with RAG capabilities - automatically retrieves relevant
                // context from vector store before generating responses
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }

    /**
     * RAG-enabled endpoint for querying AI model information with enhanced context.
     * <p>
     * This endpoint uses retrieval-augmented generation to provide accurate,
     * context-rich responses about AI models. The QuestionAnswerAdvisor automatically
     * retrieves relevant information from the vector store before generating the response.
     * <p>
     * Example usage:
     * GET /rag/models?message=What models support 128K context windows?
     * GET /rag/models?message=Compare GPT-4 and GPT-3.5 capabilities
     * GET /rag/models (uses default query for comprehensive listing)
     * <p>
     * @param message The query about AI models (default provides comprehensive list request)
     * @return Structured Models object containing AI model information with context
     *
     * @apiNote RAG Enhancement:
     *          - QuestionAnswerAdvisor automatically retrieves relevant documentation
     *          - Responses are grounded in the vector store content for accuracy
     *          - Reduces hallucination by providing factual context
     *          - Supports complex queries about model specifications
     *
     * @performance The first call may involve vector store retrieval, but responses
     *              are enhanced with accurate, up-to-date information from documents
     */
    @GetMapping("/rag/models")
    public Models faq(@RequestParam(value = "message", defaultValue = "Give me a list of all the models from OpenAI along with their context window.") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Models.class);
    }

}