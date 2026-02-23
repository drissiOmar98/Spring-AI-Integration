# 🌐 MCP Server WebFlux with Spring AI

This project demonstrates a **reactive MCP (Model Context Protocol) server** using **Spring WebFlux**, exposing AI tools over **HTTP** for high-concurrency, non-blocking communication.

It is the WebFlux variant of your MCP server ecosystem, ideal for **remote AI agents, container deployments, and cloud-based applications**.

---

## 🚀 Features

- Reactive, non-blocking **WebFlux server**
- MCP tool registration via Spring AI `@Tool`
- Integration with **Video repository** 
- Reactive HTTP endpoints for AI clients
- Easy integration with MCP clients (`mcp-client-webflux`, Web clients)
- JPA + H2 persistence for demo purposes
- ToolCallbacks registration for MCP client discovery
- Logging support and JPA schema validation

---

## 🗂️ Project Structure
```text
mcp-server-webflux/
│
├── src/main/java/com/sivalabs/mcpserverwebflux/
│   ├── McpServerWebfluxApplication.java   # Spring Boot entry point + ToolCallbacks bean
│   ├── VideoTools.java                     # MCP tools for videos
│   └── VideoRepository.java                # JPA repository
│   └── Video.java                          # JPA entity
│
├── src/main/resources/
│   ├── application.yml                     # Server configuration
│   └── data.sql / schema.sql (optional)   # Initial database setup
│
└── pom.xml                                 # Maven dependencies
```