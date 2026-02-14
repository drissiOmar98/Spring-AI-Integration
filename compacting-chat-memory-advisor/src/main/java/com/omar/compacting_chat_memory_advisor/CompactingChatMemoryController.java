package com.omar.compacting_chat_memory_advisor;


import com.omar.compacting_chat_memory_advisor.advisor.CompactingChatMemoryAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller demonstrating the compacting chat memory advisor.
 * Compare this with ChatMemoryController to see the difference between
 * regular memory (drops old messages) vs compacting memory (summarizes old messages).
 */
@RestController
@RequestMapping("/compact")
public class CompactingChatMemoryController {

    private final ChatClient chatClient;
    private final CompactingChatMemoryAdvisor compactingAdvisor;

    public CompactingChatMemoryController(ChatClient.Builder builder, CompactingChatMemoryAdvisor compactingAdvisor) {
        this.compactingAdvisor = compactingAdvisor;
        this.chatClient = builder
                .defaultAdvisors(compactingAdvisor)
                .build();
    }

    /**
     * Chat endpoint with automatic compacting.
     * When the conversation reaches the configured threshold (compact-threshold),
     * the oldest messages (messages-to-compact) will be automatically summarized
     * into a single message, preserving context while reducing memory usage.
     */
    @GetMapping("/memory")
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * Manually trigger compaction of the conversation history.
     * This allows you to compact the conversation at any time, even before
     * the automatic threshold is reached.
     *
     * @return Information about what was compacted (message counts, token savings)
     */
    @GetMapping("/trigger")
    public String triggerCompact() {
        return compactingAdvisor.compact();
    }

    /**
     * Clear the conversation history and start fresh.
     *
     * @return Confirmation message
     */
    @GetMapping("/clear")
    public String clearMemory() {
        return compactingAdvisor.clear();
    }
}