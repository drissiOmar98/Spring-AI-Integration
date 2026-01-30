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


}
