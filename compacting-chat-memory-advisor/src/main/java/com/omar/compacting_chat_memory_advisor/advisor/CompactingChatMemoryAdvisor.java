package com.omar.compacting_chat_memory_advisor.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Custom chat memory advisor that automatically compacts conversation history
 * when approaching the message limit by summarizing older messages.
 *
 * <p>This advisor is an alternative to {@link org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor}
 * that preserves context from older messages instead of dropping them. When the conversation
 * reaches the configured threshold, this advisor uses the LLM to generate a summary of the
 * oldest messages, replacing them with a single summary message.
 *
 * <h3>Use Cases:</h3>
 * <ul>
 *   <li>Long customer support conversations where early context matters</li>
 *   <li>Multi-turn code generation sessions with architectural decisions</li>
 *   <li>Research assistants building on previous Q&A exchanges</li>
 * </ul>
 *
 * <h3>Configuration:</h3>
 * <ul>
 *   <li><b>maxMessages</b>: Maximum messages to retain (e.g., 100)</li>
 *   <li><b>compactThreshold</b>: When to trigger compaction (e.g., 80)</li>
 *   <li><b>messagesToCompact</b>: How many old messages to summarize (e.g., 40)</li>
 * </ul>
 *
 * @see org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
 */
public class CompactingChatMemoryAdvisor implements CallAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(CompactingChatMemoryAdvisor.class);

    private final ChatMemory chatMemory;
    private final ChatClient summaryClient;
    private final int maxMessages;
    private final int compactThreshold;
    private final int messagesToCompact;
    private final TokenCountEstimator tokenCountEstimator;
    private static final String DEFAULT_CONVERSATION_ID = "default";

    public CompactingChatMemoryAdvisor(ChatMemory chatMemory, ChatModel chatModel,
                                       int maxMessages, int compactThreshold, int messagesToCompact) {
        // Validate configuration
        if (compactThreshold >= maxMessages) {
            throw new IllegalArgumentException(
                String.format("compactThreshold (%d) must be less than maxMessages (%d)",
                    compactThreshold, maxMessages)
            );
        }
        if (messagesToCompact >= compactThreshold) {
            throw new IllegalArgumentException(
                String.format("messagesToCompact (%d) must be less than compactThreshold (%d)",
                    messagesToCompact, compactThreshold)
            );
        }
        if (messagesToCompact < 2) {
            throw new IllegalArgumentException(
                "messagesToCompact must be at least 2"
            );
        }

        this.chatMemory = chatMemory;
        this.summaryClient = ChatClient.builder(chatModel).build();
        this.maxMessages = maxMessages;
        this.compactThreshold = compactThreshold;
        this.messagesToCompact = messagesToCompact;
        this.tokenCountEstimator = new JTokkitTokenCountEstimator();
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        String conversationId = getConversationId(request);
        logger.debug("Processing request for conversation: {}", conversationId);

        // Check if compaction is needed before processing
        checkAndCompact(conversationId);

        // Extract and add user message to memory
        String userText = request.prompt().getInstructions().stream()
                .filter(msg -> msg instanceof UserMessage)
                .map(msg -> ((UserMessage) msg).getText())
                .findFirst()
                .orElse("");

        if (!userText.isEmpty()) {
            logger.debug("Adding user message to memory for conversation {}: {}", conversationId, userText);
            chatMemory.add(conversationId, new UserMessage(userText));
        }

        // Get conversation history and augment the prompt
        List<Message> memoryMessages = chatMemory.get(conversationId);
        int tokenCount = estimateTokenCount(memoryMessages);
        logger.debug("Retrieved {} messages ({} tokens) from memory for conversation {}",
                memoryMessages.size(), tokenCount, conversationId);

        Prompt augmentedPrompt = new Prompt(memoryMessages);
        ChatClientRequest modifiedRequest = ChatClientRequest.builder()
                .prompt(augmentedPrompt)
                .context(Map.copyOf(request.context()))
                .build();

        ChatClientResponse response = chain.nextCall(modifiedRequest);

        // Add assistant response to memory
        String assistantResponse = response.chatResponse().getResult().getOutput().getText();
        logger.debug("Adding assistant response to memory for conversation {}", conversationId);
        chatMemory.add(conversationId, new AssistantMessage(assistantResponse));

        return response;
    }

    /**
     * Manually trigger compaction of conversation history.
     * @param conversationId The conversation ID to compact
     * @return Summary of what was compacted
     */
    public String compact(String conversationId) {
        logger.debug("Manual compaction requested for conversation: {}", conversationId);
        List<Message> messages = chatMemory.get(conversationId);

        if (messages.size() < messagesToCompact) {
            logger.debug("Not enough messages to compact. Current: {}, minimum: {}", messages.size(), messagesToCompact);
            return "Not enough messages to compact. Current: " + messages.size() + ", minimum: " + messagesToCompact;
        }

        return performCompaction(conversationId, messages);
    }

    /**
     * Manually trigger compaction using default conversation ID.
     */
    public String compact() {
        return compact(DEFAULT_CONVERSATION_ID);
    }

    /**
     * Clear conversation history for the specified conversation ID.
     * @param conversationId The conversation ID to clear
     * @return Confirmation message
     */
    public String clear(String conversationId) {
        logger.debug("Clearing conversation history for: {}", conversationId);
        List<Message> messages = chatMemory.get(conversationId);
        int messageCount = messages.size();
        int tokenCount = estimateTokenCount(messages);

        chatMemory.clear(conversationId);

        logger.debug("Cleared {} messages ({} tokens) from conversation {}",
                messageCount, tokenCount, conversationId);

        return String.format("Cleared conversation '%s': removed %d messages (%d tokens)",
                conversationId, messageCount, tokenCount);
    }

    /**
     * Clear conversation history using default conversation ID.
     * @return Confirmation message
     */
    public String clear() {
        return clear(DEFAULT_CONVERSATION_ID);
    }

    private void checkAndCompact(String conversationId) {
        List<Message> messages = chatMemory.get(conversationId);
        int currentTokens = estimateTokenCount(messages);
        logger.debug("Checking compaction threshold: messages={}/{}, tokens={}, conversationId={}",
                messages.size(), compactThreshold, currentTokens, conversationId);

        if (messages.size() >= compactThreshold) {
            logger.debug("Compaction threshold reached ({}/{}). Triggering compaction for conversation {} ({} tokens)",
                    messages.size(), compactThreshold, conversationId, currentTokens);
            performCompaction(conversationId, messages);
        }
    }

    private String performCompaction(String conversationId, List<Message> messages) {
        int beforeTokens = estimateTokenCount(messages);
        logger.debug("Starting compaction for conversation {}. Total messages: {}, tokens: {}, compacting oldest: {}",
                conversationId, messages.size(), beforeTokens, messagesToCompact);

        // Get the oldest messages to compact (exclude system messages)
        List<Message> messagesToSummarize = messages.stream()
                .limit(messagesToCompact)
                .collect(Collectors.toList());
        int messagesToCompactTokens = estimateTokenCount(messagesToSummarize);

        // Build conversation text for summarization (skip SystemMessage - don't re-summarize summaries)
        String conversationText = messagesToSummarize.stream()
                .filter(msg -> !(msg instanceof SystemMessage))  // Skip system messages (summaries from previous compactions)
                .map(msg -> {
                    String role = msg instanceof UserMessage ? "User" : "Assistant";
                    String text = msg instanceof UserMessage ?
                        ((UserMessage) msg).getText() :
                        ((AssistantMessage) msg).getText();
                    return role + ": " + text;
                })
                .collect(Collectors.joining("\n"));

        logger.debug("Sending {} messages ({} tokens) to LLM for summarization", messagesToCompact, messagesToCompactTokens);

        // Generate summary
        String summary = summaryClient.prompt()
                .user("Summarize the following conversation concisely, preserving key information and context:\n\n" + conversationText)
                .call()
                .content();

        int summaryTokens = tokenCountEstimator.estimate(summary);
        logger.debug("Generated summary for {} messages: {} ({} tokens, saved {} tokens)",
                messagesToCompact, summary, summaryTokens, messagesToCompactTokens - summaryTokens);

        // Clear old messages and add summary
        logger.debug("Clearing memory and rebuilding with summary for conversation {}", conversationId);
        chatMemory.clear(conversationId);

        // Add summary as a system message (more semantically appropriate than AssistantMessage)
        SystemMessage summaryMessage = new SystemMessage("Summary of previous conversation: " + summary);
        chatMemory.add(conversationId, summaryMessage);

        // Add back remaining messages (after the compacted ones)
        int remainingMessages = messages.size() - messagesToCompact;
        logger.debug("Adding {} remaining messages back to memory", remainingMessages);
        messages.stream()
                .skip(messagesToCompact)
                .forEach(msg -> chatMemory.add(conversationId, msg));

        List<Message> finalMessages = chatMemory.get(conversationId);
        int newMessageCount = finalMessages.size();
        int afterTokens = estimateTokenCount(finalMessages);
        int tokensSaved = beforeTokens - afterTokens;

        logger.debug("Compaction complete for conversation {}. Messages: {} -> {}, Tokens: {} -> {} (saved {} tokens)",
                conversationId, messages.size(), newMessageCount, beforeTokens, afterTokens, tokensSaved);

        return String.format("Compacted %d messages into summary. Messages: %d -> %d, Tokens: %d -> %d (saved %d tokens)",
                messagesToCompact, messages.size(), newMessageCount, beforeTokens, afterTokens, tokensSaved);
    }

    private String getConversationId(ChatClientRequest request) {
        return (String) request.context()
                .getOrDefault(ChatMemory.CONVERSATION_ID, DEFAULT_CONVERSATION_ID);
    }

    /**
     * Estimate the total number of tokens in a list of messages.
     */
    private int estimateTokenCount(List<Message> messages) {
        return messages.stream()
                .mapToInt(msg -> {
                    String text = msg instanceof UserMessage ?
                        ((UserMessage) msg).getText() :
                        (msg instanceof AssistantMessage ? ((AssistantMessage) msg).getText() :
                        (msg instanceof SystemMessage ? ((SystemMessage) msg).getText() : ""));
                    return tokenCountEstimator.estimate(text);
                })
                .sum();
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}