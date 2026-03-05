# 🤖 Spring AI MCP Client – Build AI-Powered Apps with MCP

> A **Spring Boot application acting as an MCP client** using **Spring AI** to connect Large Language Models with external **MCP servers** for real-time tools and contextual data.

This project demonstrates how to build **AI-powered applications that go beyond pre-trained LLM knowledge** by connecting them to **Model Context Protocol (MCP) servers**.

Using Spring AI, the application integrates with an LLM provider and dynamically invokes tools exposed by an MCP server — in this case **Dan Vega as a Service (DVaaS)**.

---

## 🚀 Overview

Large Language Models are powerful, but they have limitations:

- ❌ Knowledge cutoff
- ❌ No access to proprietary data
- ❌ No real-time information
- ❌ No external tool execution

The **Model Context Protocol (MCP)** solves this problem by allowing AI models to **call external tools and retrieve live data** during inference.

This project shows how **Spring Boot + Spring AI** can act as an **MCP client** that connects to MCP servers and augments LLM responses with external capabilities.

---

## ✨ Features

This project combines **Spring Boot**, **Spring AI**, and **MCP servers** to build a fully **dynamic AI-powered application**. Key features include:

- **🤖 Spring AI MCP Client Integration**  
  Leverages Spring AI's MCP client starter for **seamless connectivity** to MCP servers and automatic tool registration.

- **🌐 HTTP / Streamable Transport**  
  Connects to the **DVaaS MCP server** using **streamable HTTP transport** for reliable, real-time communication.

- **🛠️ Automatic Tool Discovery**  
  Automatically discovers and registers **21+ tools** across YouTube, Blog, Speaking, Newsletter, and Podcast categories.

- **💬 OpenAI Chat Integration**  
  Uses **OpenAI chat models** with **function calling** to interact dynamically with MCP tools for enriched AI responses.

- **📡 REST API**  
  Exposes a simple `/chat` endpoint for **testing AI-powered queries**, enabling real-time interactive responses.



## ⚙️ How It Works

This project demonstrates how a **Spring AI MCP Client** interacts with an **MCP server** to enhance LLM responses with real-time tools and data.

---

### 1️⃣ MCP Client Initialization

The Spring AI **MCP Client Boot Starter** handles initialization automatically:

- Connects to the **DVaaS MCP server** at application startup
- Discovers all available tools (21+ tools across **YouTube, blog, speaking, newsletter, and podcast** categories)
- Registers these tools as **function callbacks** that the AI model can invoke dynamically

This ensures the AI can leverage external context **without any manual wiring**.

---

### 2️⃣ Chat Controller

The `ChatController` demonstrates a **fully functional AI chat endpoint**:

```java
@RestController
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider tools) {
        // Log all discovered MCP tools for observability
        Arrays.stream(tools.getToolCallbacks()).forEach(t -> {
            log.info("Tool Callback found: {}", t.getToolDefinition());
        });

        // Build ChatClient with all MCP tool callbacks
        this.chatClient = builder
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/chat")
    public String chat() {
        return chatClient.prompt()
                .user("What are Dan Vega's latest YouTube videos")
                .call()
                .content();
    }
}
```
### 3️⃣ AI Function Calling Flow

The sequence of operations when a user interacts with the `/chat` endpoint:

1. **User sends a query** to the `/chat` endpoint.
2. **Spring AI ChatClient** forwards the prompt to the LLM (e.g., OpenAI).
3. The **LLM determines which MCP tools** need to be invoked based on the query.
4. **Spring AI executes the MCP tool calls** via the DVaaS MCP server.
5. **Tool results are returned** to the LLM.
6. The LLM **generates a natural language response** using the tool outputs.
7. **Response is sent back** to the user via the REST endpoint.

> This architecture enables the AI to access **real-time information and custom functionality** without modifying the underlying LLM.  
> The result is a **dynamic, extensible, and context-aware application** capable of integrating external tools seamlessly.

## 🛠️ Available DVaaS Tools

The **DVaaS MCP server** exposes **21 tools** across **5 main categories**, providing a rich set of capabilities that the AI can call dynamically via the MCP client.

---

### 📺 YouTube Tools (4)
- **Search videos** – Find relevant videos based on keywords or topics
- **Get channel statistics** – Retrieve metrics such as subscribers and views
- **Find topic-based tutorials** – Locate educational content on specific subjects
- **Retrieve video metadata** – Access detailed info including titles, descriptions, and durations

---

### 📝 Blog Tools (4)
- **Discover posts** – Browse available blog posts
- **Search by keywords** – Find posts matching specific search terms
- **Get post details** – Retrieve full post content and metadata
- **Browse categories** – Explore posts by category or tag

---

### 🎤 Speaking Tools (4)
- **View upcoming events** – List scheduled speaking events
- **Access event archives** – Retrieve historical events and talks
- **Get event details** – Fetch metadata like location, speakers, and agenda
- **Search by date range** – Filter events by specific dates

---

### 📰 Newsletter Tools (4)
- **Access newsletter issues** – View latest or past issues
- **Search archives** – Find specific content within newsletters
- **Get subscriber statistics** – Retrieve metrics such as open rates and counts
- **Filter by topic** – Narrow down newsletters by category or subject

---

### 🎙️ Podcast Tools (5)
- **Browse episodes** – List all podcast episodes
- **Search transcripts** – Find content within episode transcripts
- **View guest information** – Retrieve details about podcast guests
- **Access show notes** – Read notes or summaries of episodes
- **Get episode details** – Fetch metadata including duration, release date, and description

---

> 💡 **Tip:** Each of these tools is automatically discovered by the Spring AI MCP client and can be invoked dynamically by the LLM, enabling **real-time, context-aware responses** without any additional coding.


## 📦 Dependencies

This project leverages a set of key dependencies to integrate **Spring Boot**, **Spring AI**, and **MCP client functionality** with LLMs like OpenAI.

```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- Provides REST API support, web server, and MVC framework for building endpoints -->

<!-- Spring AI MCP Client -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-client</artifactId>
</dependency>
<!-- Enables the application to act as an MCP client, discovering and invoking external MCP tools -->

<!-- Spring AI OpenAI Integration -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>
<!-- Integrates OpenAI as an LLM provider, allowing prompts to be sent to OpenAI models -->
```

## ⚡ Extending the Application

This project is designed to be **extensible**, allowing you to easily integrate multiple MCP servers and expand AI capabilities.

---

### 🔗 Adding More MCP Servers

You can connect to **multiple MCP servers** by adding additional entries under `streamable-http.connections` in your `application.yml`:

```yaml
spring:
  ai:
    mcp:
      client:
        streamable-http:
          connections:
            # DVaaS MCP server providing tools across YouTube, Blog, Speaking, Newsletter, Podcast
            dvaas:
              url: https://mcp.danvega.dev/mcp

            # Another MCP server can be added here
            another-server:
              url: https://another-mcp-server.com/mcp
```

### ✏️ Customizing Chat Prompts

You can make the chat endpoint **dynamic** by allowing users to send custom queries.  
Update the `chat()` method in `ChatController`:

```java
@GetMapping("/chat")
public String chat(@RequestParam String question) {
    return chatClient.prompt()
            .user(question)   // Send the user-provided question to the LLM
            .call()           // Execute the request through Spring AI
            .content();       // Return the generated response
}
```

### 🖥️ Adding STDIO MCP Servers

You can run **local MCP servers** using the **STDIO transport**, which allows the Spring AI MCP client to communicate with tools directly via standard input/output.

#### Example Configuration (`application.yml`):

```yaml
spring:
  ai:
    mcp:
      client:
        stdio:
          connections:
            local-server:
              command: /path/to/mcp-server      # Path to your local MCP server executable
              args:                             # Optional command-line arguments
                - --config
                - /path/to/config.json

```

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| ☕ Java 21 | Core programming language |
| 🌱 Spring Boot | Application framework |
| 🧠 Spring AI | LLM & MCP integration |
| 🤖 OpenAI API | LLM provider |
| 🔌 MCP | Model Context Protocol |
| 🌐 REST API | AI endpoint |