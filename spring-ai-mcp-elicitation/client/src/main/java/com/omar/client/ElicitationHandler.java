package com.omar.client;

import io.modelcontextprotocol.spec.McpSchema.ElicitRequest;
import io.modelcontextprotocol.spec.McpSchema.ElicitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpElicitation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class ElicitationHandler {

    private static final Logger log = LoggerFactory.getLogger(ElicitationHandler.class);
    private final Scanner scanner = new Scanner(System.in);

    public ElicitationHandler() {
        log.info("Elicitation handler initialized");
    }

    @SuppressWarnings("unchecked")
    @McpElicitation(clients = "coffeeshop")
    public ElicitResult handleElicitation(ElicitRequest request) {
        System.out.println("\n=== " + request.message() + " ===\n");

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> properties = (Map<String, Object>) request.requestedSchema().get("properties");

        for (String field : properties.keySet()) {
            System.out.print(field + ": ");
            data.put(field, scanner.nextLine().trim());
        }

        return new ElicitResult(ElicitResult.Action.ACCEPT, data);
    }
}
