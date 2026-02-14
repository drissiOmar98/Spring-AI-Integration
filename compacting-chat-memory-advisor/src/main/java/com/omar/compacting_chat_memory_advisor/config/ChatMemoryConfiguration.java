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


}