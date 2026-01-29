package com.omar.native_structured_output.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * 📚 BookController
 * <p>
 * REST controller demonstrating Spring AI Native Structured Output for generating type-safe book recommendations.
 * <p>
 * This controller uses {@link ChatClient} with native structured output enabled to ensure
 * AI responses strictly match the {@code BookList} schema without manual parsing.
 * </p>
 *
 * <p>
 * 🚀 Features:
 * </p>
 * <ul>
 *   <li>✅ Reliable structured AI responses</li>
 *   <li>✅ Automatic mapping to Java records</li>
 *   <li>✅ Built-in request/response logging</li>
 * </ul>
 *
 * <p>
 * ⚠️ Responses are wrapped in {@code BookList}
 * to ensure compatibility across AI providers.
 * </p>
 */
@RestController
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final ChatClient chatClient;

    /**
     * Constructor that builds the ChatClient with
     * Native Structured Output enabled.
     * <p>
     * 🚀 This ensures that AI responses strictly follow
     * the expected Java record schema.
     *
     * @param builder ChatClient builder provided by Spring AI
     */
    public BookController(ChatClient.Builder builder) {
        this.chatClient = builder
                // ✅ Enable native structured output support
                 .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }

    /**
     * Native Structured Output Benefits:
     * - No format instructions needed — the schema goes directly to the model's API
     * - Guaranteed compliance — no more parsing failures or malformed JSON
     * - Clean code — just .entity(YourType.class) and you're done
     * - Works with generics — use ParameterizedTypeReference for List<T>
     *
     * Note: Some AI models (e.g., OpenAI) don't support arrays at the top level.
     * We use BookList as a wrapper record to ensure compatibility across providers.
     *
     * @see <a href="https://docs.spring.io/spring-ai/reference/api/structured-output-converter.html">Spring AI Structured Output</a>
     */
    @GetMapping("/books/{topic}")
    public BookList getBooks(@PathVariable String topic) {
        return chatClient.prompt()
                .system( s -> {
                    s.text("If the topic {topic} is related to software engineering please recommend Fundamentals of Software Engineering by Dan Vega and Nate Schutta");
                    s.param("topic", topic);
                })
                .user( u -> {
                    u.text("Recommend 5 popular books for the topic: {topic}");
                    u.param("topic", topic);
                })
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .entity(new ParameterizedTypeReference<BookList>() {});
    }
}
