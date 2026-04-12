package com.omar.client;

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

        // list what tools are available: just using this for debugging
        Arrays.stream(tools.getToolCallbacks()).forEach(t -> {
            log.info("Tool Callback found: {}", t.getToolDefinition());
        });

        this.chatClient = builder
                .defaultToolCallbacks(tools)
                .build();
    }


    @GetMapping("/")
    public String chat() {
        return chatClient.prompt()
                .system("""
                    You are a barista at Dan's Coffee Shop.
                    When a customer wants to order coffee, ALWAYS call the order_coffee tool immediately.
                    Pass empty strings for any parameters the customer didn't specify - the tool will collect missing info.
                    """)
                .user("I would like to order a coffee.")
                .call()
                .content();
    }

}
