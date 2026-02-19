package com.omar.spring_io_mcp_server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * MCP tool component exposing Spring I/O conference session data
 * to AI assistants via the Model Context Protocol (MCP).
 *
 * <p>This component loads session information from a local JSON file
 * at application startup and makes it accessible through a registered
 * MCP tool. It allows AI models to query conference sessions such as
 * talks, workshops, speakers, and scheduling details.</p>
 *
 * <p>The tool is designed for read-only access and is optimized for
 * fast lookup and structured responses suitable for LLM consumption.</p>
 */
@Component
public class SessionTools {

    private static final Logger log = LoggerFactory.getLogger(SessionTools.class);
    /**
     * In-memory list of conference sessions loaded from JSON.
     */
    private List<Session> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper;

    public SessionTools(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * MCP tool that returns all available Spring I/O conference sessions.
     *
     * <p>This tool can be invoked by AI assistants to retrieve the complete
     * list of sessions, including metadata such as day, time, title,
     * session type, speakers, and room.</p>
     *
     * @return list of all conference sessions
     */
    @Tool(name = "spring-io-sessions", description = "Returns all sessions for Spring I/O 2025 Conference")
    public List<Session> findAllSessions() {
        return sessions;
    }

    /**
     * Initializes the session catalog at application startup.
     *
     * <p>Reads the {@code sessions.json} file from the classpath,
     * deserializes it into a {@link Conference} object, and extracts
     * the session list into memory.</p>
     *
     * @throws RuntimeException if the JSON file cannot be read or parsed
     */
    @PostConstruct
    public void init() {
        log.info("Loading Sessions from JSON file 'sessions.json'");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/sessions.json")) {
            var conference = objectMapper.readValue(inputStream, Conference.class);
            this.sessions = conference.sessions();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }
}