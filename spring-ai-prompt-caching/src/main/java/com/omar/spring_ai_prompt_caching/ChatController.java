package com.omar.spring_ai_prompt_caching;

import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.anthropic.api.AnthropicCacheOptions;
import org.springframework.ai.anthropic.api.AnthropicCacheStrategy;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 🎯 ChatController
 *
 * <p>
 * Demonstrates how to use <b>Anthropic Prompt Caching</b> with Spring AI
 * to significantly reduce token usage and API costs.
 * </p>
 *
 * <p>
 * This controller:
 * </p>
 * <ul>
 *   <li>Loads a reusable system prompt from an external file</li>
 *   <li>Applies Anthropic's <b>SYSTEM_ONLY</b> prompt caching strategy</li>
 *   <li>Sends user prompts dynamically while reusing cached system content</li>
 *   <li>Logs cache creation and cache read tokens to verify savings</li>
 * </ul>
 *
 * <p>
 * Ideal for applications with large or expensive system prompts that rarely change.
 * </p>
 */
@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final String systemPrompt;

    /**
     * 🏗️ Constructs the ChatController.
     *
     * <p>
     * - Builds the {@link ChatClient} using Spring AI auto-configuration<br>
     * - Loads the system prompt from a classpath resource to enable caching
     * </p>
     *
     * @param chatClient Spring AI ChatClient builder
     * @param systemPromptResource system prompt file loaded from the classpath
     */
    public ChatController(ChatClient.Builder chatClient, @Value("classpath:system-prompt.txt") Resource systemPromptResource) throws IOException {
        this.chatClient = chatClient.build();
        // Load system prompt from file so it can be cached across requests
        this.systemPrompt = systemPromptResource.getContentAsString(StandardCharsets.UTF_8);
    }


    /**
     * 💬 Executes a chat request using Anthropic prompt caching.
     *
     * <p>
     * The system prompt is cached using the <b>SYSTEM_ONLY</b> strategy,
     * meaning it is sent once and reused for subsequent requests.
     * Only the user prompt is sent every time.
     * </p>
     *
     * @return AI-generated content optimized for multiple platforms
     */
    @GetMapping("/")
    public String chat() {
        // ⚙️ Configure Anthropic chat options with prompt caching enabled
        AnthropicChatOptions chatOptions = AnthropicChatOptions.builder()
                .model(AnthropicApi.ChatModel.CLAUDE_SONNET_4_5)
                // Cache only the system prompt to reduce repeated token usage
                .cacheOptions(AnthropicCacheOptions.builder().strategy(AnthropicCacheStrategy.SYSTEM_ONLY).build())
                .build();

        // User prompt template (dynamic content, not cached)
        String userPrompt = """
            Generate platform-specific posts for X, Bluesky, and LinkedIn.
            
            Video information:
            - Title: {title}
            - URL: {url}
            - Description: {description}
            """;

        ChatResponse response = chatClient.prompt()
                .options(chatOptions)
                // Cached system prompt
                .system(systemPrompt)
                // Dynamic user input
                .user(u -> {
                    u.text(userPrompt);
                    u.param("title", "Spring AI Prompt Caching");
                    u.param("url", "https://youtube.com/your-video");
                    u.param("description",
                            "How prompt caching works in Spring AI with Anthropic models.");
                })
                .call()
                .chatResponse();

        // 📊 Extract Anthropic-specific usage metadata
        AnthropicApi.Usage usage = (AnthropicApi.Usage) response.getMetadata().getUsage().getNativeUsage();

        // 🧾 Log cache statistics to verify caching behavior
        if (usage != null) {
            System.out.println("Cache creation: " + usage.cacheCreationInputTokens());
            System.out.println("Cache read: " + usage.cacheReadInputTokens());
        }

        return response.getResult().getOutput().getText();
    }
}