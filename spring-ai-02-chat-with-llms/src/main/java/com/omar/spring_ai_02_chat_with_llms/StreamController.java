package com.omar.spring_ai_02_chat_with_llms;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * StreamController
 * REST controller that demonstrates two types of AI response delivery using Spring AI:
 * 1️⃣ **Full response endpoint** – Returns the complete response from the LLM in a single HTTP call.
 * 2️⃣ **Streaming endpoint (SSE)** – Streams the AI response in real-time token-by-token or chunk-by-chunk,
 *    ideal for applications that want immediate feedback or a dynamic UI experience.
 * Base URL: /api/stream
 */
@RestController
@RequestMapping("/api/stream")
public class StreamController {

    private final ChatClient chatClient;

    public StreamController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 🔹 Full Response Endpoint
     * Endpoint: GET /api/stream/full
     * Accepts a user message as a query parameter and sends it to the AI model.
     * Returns the full response in one shot.
     * Default message if none provided: "Give me 5 Spring Boot best practices"
     * @param message The user prompt to send to the AI model.
     * @return Output object containing the complete AI-generated text.
     */
    @GetMapping("/full")
    public Output fullResponse(
            @RequestParam(value = "message",
                    defaultValue = "Give me 5 Spring Boot best practices") String message) {

        String content = chatClient
                .prompt(message)
                .call()
                .content();

        return new Output(content);
    }

    /**
     * 🔹 Streaming Endpoint (SSE)
     * Endpoint: GET /api/stream/sse
     * Produces: text/event-stream
     * Streams the AI response as it is generated, token-by-token or chunk-by-chunk.
     * Perfect for real-time UIs or applications where partial results improve UX.
     * <p>
     * Default message if none provided:
     * "Stream 5 tips to improve Spring AI applications"
     *
     * @param message The user prompt to send to the AI model.
     * @return Flux<String> that streams partial responses from the AI model.
     */
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponse(
            @RequestParam(value = "message",
                    defaultValue = "Stream 5 tips to improve Spring AI applications") String message) {

        return chatClient
                .prompt()      // Start building the prompt
                .user(message) // Add user message
                .stream()      // Enable streaming mode
                .content();    // Return the content as a Flux
    }

    public record Output(String content) {}
}
