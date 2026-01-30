package com.omar.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller demonstrating Spring AI ChatClient usage with different configurations.
 *
 * <p>This controller shows how to control LLM behavior for various use cases
 * such as creative writing, factual Q&A, and code generation using OpenAI chat options.</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li>GET /           - Default GPT-5 chat example</li>
 *     <li>GET /creative   - Creative writing example with high temperature</li>
 *     <li>GET /facts      - Factual Q&A example with low temperature</li>
 *     <li>GET /code       - Code generation example with stop sequences</li>
 * </ul>
 */
@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Default GPT-5 chat example.
     * <p>
     * Demonstrates a basic interaction with GPT-5 using moderate temperature
     * to produce interesting responses.
     *
     * @return a ChatResponse containing the AI-generated content
     */
    @GetMapping("/")
    public ChatResponse chat() {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-5")
                .temperature(1.0) // Controls creativity: higher = more creative
                .build();

        return chatClient.prompt()
                .user("Tell me an interesting fact about GPT-5")
                .options(options)
                .call()
                .chatResponse();
    }

    /**
     * Creative writing example.
     * <p>
     * Shows how to configure the chat client for storytelling:
     * - High temperature for creativity
     * - Presence penalty to encourage topic diversity
     * - Max tokens limit to control response length
     *
     * @return a short AI-generated story
     */
    @GetMapping("/creative")
    public String creativeWriting() {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(1.0)         // High creativity
                .presencePenalty(0.6)     // Encourage new ideas
                .maxTokens(150)           // Limit output length
                .build();

        return chatClient.prompt()
                .user("Write a short creative story about a robot")
                .options(options)
                .call()
                .content();
    }

    /**
     * Factual Q&A example.
     * <p>
     * Demonstrates low temperature for deterministic answers and
     * frequency penalty to reduce repetition.
     *
     * @return AI-generated factual response
     */
    @GetMapping("/facts")
    public String factualExample() {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.1)        // Low creativity, factual
                .frequencyPenalty(0.0)   // No repetition penalty
                .maxTokens(50)           // Short answer
                .build();

        return chatClient.prompt()
                .user("What is the capital of France? Provide a factual answer.")
                .options(options)
                .call()
                .content();
    }

    /**
     * Code generation example.
     * <p>
     * Shows how to configure the chat client for coding tasks:
     * - Moderate temperature for deterministic code
     * - Stop sequences to delimit code blocks
     * - Max tokens to prevent overly long outputs
     *
     * @return AI-generated Java code snippet
     */
    @GetMapping("/code")
    public String codeGen() {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.3)                 // Slight creativity for coding
                .maxTokens(200)                   // Limit output
                .stop(List.of("END_CODE", "\n\n---")) // Stop sequences to delimit code
                .build();

        return chatClient.prompt()
                .user("Write a Java method to calculate factorial. Start with ```java and end with ```")
                .options(options)
                .call()
                .content();
    }

}
