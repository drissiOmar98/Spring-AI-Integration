package com.omar.chat_memory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 💬 Chat Controller
 * <p>
 * Provides conversational AI capabilities with built-in memory and session management.
 * Maintains conversation context across requests using cookies for multi-turn dialogues.
 * <p>
 * Endpoint: POST /api/chat
 * <p>
 * Features:
 * - Persistent conversation memory using chat memory advisor
 * - Automatic conversation ID management via cookies
 * - Request/response logging for debugging
 * - Input validation and structured response format
 * <p>
 * Use Cases:
 * - Chatbots with memory context
 * - Multi-turn conversational interfaces
 * - Session-based AI assistants
 * - Context-aware dialogue systems
 */
@RestController
@RequestMapping("/")
class ChatController {

    /**
     * Spring AI ChatClient for interacting with AI models.
     * Configured with memory management and logging advisors for enhanced functionality.
     */
    private final ChatClient chatClient;

    /**
     * Constructor that builds the ChatClient with memory and logging capabilities.
     *
     * @param builder ChatClient builder for creating configured chat instances
     * @param chatMemory Chat memory implementation for maintaining conversation context
     */
    ChatController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(
                        // Advisor for maintaining conversation memory across requests
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        // Advisor for logging chat interactions
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    /**
     * Processes chat messages with conversation memory and session management.
     * <p>
     * This endpoint handles AI chat interactions while maintaining conversation context.
     * Uses cookies to track conversation IDs for continuous dialogue sessions.
     * <p>
     * Example Request:
     * POST /api/chat
     * Cookie: X-CONV-ID=123e4567-e89b-12d3-a456-426614174000
     * Body: {"prompt": "Hello, how are you?"}
     * <p>
     * Example Response:
     * Set-Cookie: X-CONV-ID=123e4567-e89b-12d3-a456-426614174000
     * Body: {"content": "I'm doing well, thank you! How can I assist you today?"}
     *
     * @param input Validated input containing the user's message prompt
     * @param convId Conversation ID from cookie (optional, generated if not provided)
     * @return ResponseEntity containing AI response and conversation cookie
     *
     * @apiNote Key Features:
     *          - Automatic conversation ID generation if not provided
     *          - 1-hour session cookie lifetime
     *          - Memory persists conversation context across requests
     *          - All interactions are logged via SimpleLoggerAdvisor
     *          - Input validation ensures non-empty prompts
     */
    @PostMapping("/api/chat")
    ResponseEntity<Output> chat(@RequestBody @Valid Input input,
                                @CookieValue(name = "X-CONV-ID", required = false) String convId) {

        // Generate or use existing conversation ID for memory context
        String conversationId = convId == null ? UUID.randomUUID().toString() : convId;

        // Process the chat prompt with conversation context
        var response = this.chatClient.prompt()
                .user(input.prompt())   // User's input message
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)) // Set conversation context
                .call().content();      // Execute and get AI response

        // Create session cookie to maintain conversation continuity
        ResponseCookie cookie = ResponseCookie.from("X-CONV-ID", conversationId)
                .path("/")                      // Available across entire application
                .maxAge(3600)     // 1 hour session duration
                .build();

        // Prepare response with AI content
        Output output = new Output(response);

        // Return response with conversation cookie
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(output);
    }


    /**
     * Input DTO for chat requests.
     * Uses Java records for immutable data transfer.
     *
     * @param prompt The user's message (validated to be non-blank)
     */
    record Input(@NotBlank String prompt) {
    }

    /**
     * Output DTO for chat responses.
     * Uses Java records for immutable data transfer.
     *
     * @param content The AI's generated response
     */
    record Output(String content) {
    }

}