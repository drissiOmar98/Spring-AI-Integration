package com.omar.coffeeshop.coffee;

import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springaicommunity.mcp.context.McpSyncRequestContext;
import org.springaicommunity.mcp.context.StructuredElicitResult;
import org.springframework.stereotype.Component;

@Component
public class CoffeeTools {

    private static final Logger log = LoggerFactory.getLogger(CoffeeTools.class);

    @McpTool(name = "order-coffee", description = "Place an order at Dan's Coffee Shop. Use this tool whenever a customer wants to order coffee.")
    public String orderCoffee(
            @McpToolParam(description = "Coffee size") String size,
            @McpToolParam(description = "Coffee type") String type,
            @McpToolParam(description = "Milk preference") String milk,
            McpSyncRequestContext ctx) {

        log.info("orderCoffee called with size={}, type={}, milk={}", size, type, milk);

        // Got everything? Skip elicitation
        if (size != null && !size.isBlank() && type != null && !type.isBlank() && milk != null && !milk.isBlank()) {
            return "☕ Order confirmed: %s %s with %s milk".formatted(size, type, milk);
        }

        // Missing info - ask the user
        if (!ctx.elicitEnabled()) {
            return "Please provide size, type, and milk.";
        }

        StructuredElicitResult<CoffeeOrder> result = ctx.elicit(
                e -> e.message("Let's complete your coffee order:"),
                CoffeeOrder.class
        );

        if (result.action() != McpSchema.ElicitResult.Action.ACCEPT) {
            return "Order cancelled.";
        }

        CoffeeOrder order = result.structuredContent();
        return "☕ Order confirmed: %s %s with %s milk".formatted(
                order.size(), order.type(), order.milk()
        );
    }

}