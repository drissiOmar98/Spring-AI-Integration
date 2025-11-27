package com.omar.prompt_templates.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acme")
public class AcmeBankController {

    private final ChatClient chatClient;

    /**
     * Constructor-based dependency injection of ChatClient.Builder.
     * The builder is customized with a SimpleLoggerAdvisor to log prompts
     * and responses for debugging or monitoring purposes.
     *
     * @param builder ChatClient.Builder injected by Spring
     */
    public AcmeBankController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    /**
     * Endpoint: GET /acme/chat
     * <p>
     * Sends a user message to the AI model with strict system instructions
     * for handling customer service inquiries for AcmeBank.
     * <p>
     * System instructions:
     * - Only answer questions about account balances, transactions, branch locations/hours, and general banking services.
     * - For any off-topic questions, respond with:
     *   "I can only help with banking-related questions."
     * <p>
     * Example usage:
     *   GET /acme/chat?message=What is my account balance?
     *
     * @param message The user prompt/question to send to the AI
     * @return AI-generated response as a plain string
     */
    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        var systemInstructions = """
        You are a customer service assistant for AcmeBank.
        You can ONLY discuss:
        - Account balances and transactions
        - Branch locations and hours
        - General banking services
        
        If asked about anything else, respond: "I can only help with banking-related questions."
        """;

        return chatClient.prompt()
                .user(message)
                .system(systemInstructions)
                .call()
                .content();
    }
}
