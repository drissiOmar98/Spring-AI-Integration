package com.omar.mcps;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class exposing MCP (Model Context Protocol) tools.
 *
 * <p>
 * This service defines AI-accessible tools using {@code @McpTool} annotations.
 * These tools can be invoked by MCP clients (e.g., AI agents, MCP Inspector)
 * and are secured via Spring Security.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *     <li>Provide utility tools (e.g., echo)</li>
 *     <li>Expose security context information</li>
 *     <li>Demonstrate integration between Spring AI and Spring Security</li>
 * </ul>
 * </p>
 */
@Service
public class McpToolsService {

    /**
     * Echo tool that returns the given message along with a timestamp.
     *
     * <p>
     * Useful for:
     * <ul>
     *     <li>Testing MCP connectivity</li>
     *     <li>Debugging tool invocation</li>
     * </ul>
     * </p>
     *
     * @param message the message to echo back (required)
     * @return a formatted string containing the timestamp and the message
     */
    @McpTool(name = "echo", description = "Echo back a message with timestamp")
    public String echo(@McpToolParam(description = "Message to echo", required = true) String message) {
        // Generate current timestamp in ISO format
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Return formatted response
        return String.format("[%s] Echo: %s", timestamp, message);
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * <p>
     * This tool demonstrates how MCP integrates with Spring Security by
     * accessing the {@link SecurityContextHolder}.
     * </p>
     *
     * <p>
     * Returned information includes:
     * <ul>
     *     <li>Username (principal name)</li>
     *     <li>Authentication status</li>
     *     <li>User roles/authorities</li>
     * </ul>
     * </p>
     *
     * @return a map containing user authentication details
     */
    @McpTool(name = "getCurrentUser", description = "Get information about the currently authenticated user")
    public Map<String, Object> getCurrentUser() {
        // Retrieve authentication from Spring Security context
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // Prepare response map
        Map<String, Object> userInfo = new HashMap<>();

        // Populate user details
        userInfo.put("name", authentication.getName()); // username / subject
        userInfo.put("authenticated", authentication.isAuthenticated()); // auth status
        userInfo.put("authorities",
                authentication.getAuthorities()
                        .stream()
                        .map(Object::toString)
                        .toList()); // roles/permissions

        return userInfo;
    }

}
