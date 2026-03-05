package com.omar.spring_ai_mcp_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider tools) {

        // Log all MCP tools discovered by the client
        // This helps verify that the MCP server successfully exposed its tools
        Arrays.stream(tools.getToolCallbacks()).forEach(t -> {
            log.info("Tool Callback found: {}", t.getToolDefinition());
        });

        // Build the ChatClient and register MCP tool callbacks
        // This allows the LLM to automatically call MCP tools when needed
        this.chatClient = builder
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/chat")
    public String chat() {
        // Send a prompt to the LLM
        // The model can automatically call MCP tools from the DVaaS server
        // to retrieve up-to-date information about Dan Vega
        return chatClient.prompt()
                .user("What are Dan Vega's latest YouTube videos")
                .call()
                .content();
    }
}