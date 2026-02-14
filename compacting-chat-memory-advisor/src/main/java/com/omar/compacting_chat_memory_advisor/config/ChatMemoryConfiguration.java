package com.omar.compacting_chat_memory_advisor.config;

import com.omar.compacting_chat_memory_advisor.advisor.CompactingChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(CompactingMemoryProperties.class)
public class ChatMemoryConfiguration {

    /**
     * Regular chat memory for the standard controller (no compacting).
     * Uses default MessageWindowChatMemory behavior - drops old messages when limit reached.
     */
    @Bean
    public ChatMemory regularChatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }

    /**
     * Separate chat memory instance for the compacting advisor.
     * This keeps the two conversation histories independent for comparison.
     */
    @Bean
    public ChatMemory compactingChatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }

    /**
     * Primary chat model (OpenAI GPT-5) for user-facing chat responses.
     * Marked as @Primary to resolve ambiguity when multiple ChatModel beans exist.
     */
    @Bean
    @Primary
    public ChatModel primaryChatModel(OpenAiChatModel openAiChatModel) {
        return openAiChatModel;
    }

    /**
     * Google Gemini ChatModel bean for use in summarization.
     * This demonstrates using a cheaper/faster model for summaries while keeping
     * the primary model (OpenAI) for chat responses.
     */
    @Bean
    @Qualifier("geminiChatModel")
    public ChatModel geminiChatModel(GoogleGenAiChatModel googleGenAiChatModel) {
        return googleGenAiChatModel;
    }

    /**
     * Custom compacting advisor that automatically summarizes old messages
     * when the conversation history approaches the limit.
     *
     * Uses Google Gemini 2.5 Flash for cost-effective summarization while
     * the main chat responses use OpenAI GPT-5.
     *
     * Configuration is externalized to application.properties under the prefix "compact.memory".
     */
    @Bean
    public CompactingChatMemoryAdvisor compactingChatMemoryAdvisor(
            ChatMemory compactingChatMemory,
            @Qualifier("geminiChatModel") ChatModel geminiChatModel,
            CompactingMemoryProperties properties) {
        return new CompactingChatMemoryAdvisor(
                compactingChatMemory,
                geminiChatModel,  // Use Gemini for cost-effective summarization
                properties.maxMessages(),
                properties.compactThreshold(),
                properties.messagesToCompact()
        );
    }
}