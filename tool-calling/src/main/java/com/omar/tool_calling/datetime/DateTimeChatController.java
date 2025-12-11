package com.omar.tool_calling.datetime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 💬 DateTime Chat Controller - Tool-Enabled AI Chat Endpoint
 * <p>
 * REST controller that demonstrates AI tool calling capabilities.
 * Integrates custom DateTime tools with Spring AI ChatClient for
 * enhanced conversational AI with external function execution.
 * <p>
 * Endpoint: GET /tools
 * <p>
 * Architecture:
 * User → Chat Prompt → AI Model → Tool Detection →
 * Tool Execution → Result Integration → AI Response
 * <p>
 * Key Benefits:
 * - Extends AI capabilities beyond pre-trained knowledge
 * - Enables real-time data integration
 * - Provides structured function calling from natural language
 * - Maintains conversational context while executing tools
 */
@RestController
public class DateTimeChatController {

    private final ChatClient chatClient;

    public DateTimeChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    /**
     * 🔹 Tool Invocation Endpoint
     * <p>
     * Sends a prompt to the AI asking about tomorrow's date.
     * The AI can call the `DatTimeTools` dynamically to retrieve the current datetime.
     * <p>
     * Endpoint: GET /tools
     *
     * @return AI-generated response that may include the result from DatTimeTools
     */
    @GetMapping("/tools")
    public String tools() {
        return chatClient.prompt("What day is tomorrow?")
                .tools(new DateTimeTools())
                .call()
                .content();
    }




}