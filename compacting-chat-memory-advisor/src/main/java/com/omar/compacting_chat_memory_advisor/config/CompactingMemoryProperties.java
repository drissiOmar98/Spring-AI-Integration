package com.omar.compacting_chat_memory_advisor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the compacting chat memory advisor.
 * Configure these values in application.properties using the prefix "compact.memory".
 */
@ConfigurationProperties(prefix = "compact.memory")
public record CompactingMemoryProperties(
        /**
         * Maximum number of messages to retain in memory
         */
        int maxMessages,

        /**
         * Threshold at which to trigger automatic compaction
         */
        int compactThreshold,

        /**
         * Number of oldest messages to compact/summarize when threshold is reached
         */
        int messagesToCompact
) {
}