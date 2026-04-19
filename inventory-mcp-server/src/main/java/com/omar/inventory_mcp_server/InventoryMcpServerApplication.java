package com.omar.inventory_mcp_server;

import com.omar.inventory_mcp_server.service.ProductService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Main entry point for the Inventory MCP Server application.
 *
 * <p>This Spring Boot application starts an MCP (Model Context Protocol) server
 * that exposes product inventory tools to AI models.
 *
 * <p>It configures startup behavior, disables unnecessary logging, ensures
 * graceful shutdown, and registers MCP tools from the ProductService.
 */
@SpringBootApplication
public class InventoryMcpServerApplication {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(InventoryMcpServerApplication.class);
		app.setLogStartupInfo(false);
		app.setBannerMode(Banner.Mode.OFF);

		ConfigurableApplicationContext context = app.run(args);

		// Add shutdown hook for graceful shutdown
		Runtime.getRuntime().addShutdownHook(new Thread(context::close));

		// CRITICAL: Keep the application running
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	@Bean
	public ToolCallbackProvider productTools(ProductService productService) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(productService)
				.build();
	}

}
