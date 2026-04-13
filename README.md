# 🤖 Spring AI Integration

<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20AI-1.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/OpenAI-GPT--4-412991?style=for-the-badge&logo=openai&logoColor=white"/>
  <img src="https://img.shields.io/badge/Ollama-Local%20LLM-000000?style=for-the-badge&logo=ollama&logoColor=white"/>
  <img src="https://img.shields.io/badge/Anthropic-Claude-C26D11?style=for-the-badge&logo=anthropic&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge"/>
</p>

> A curated, production-grade collection of Spring Boot modules demonstrating AI and LLM integrations — covering multi-provider chat, RAG pipelines, tool calling, MCP servers/clients, chat memory, multimodality, structured outputs, prompt engineering, observability, and much more.

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Repository Structure](#-repository-structure)
- [Modules](#-modules)
  - [Core Chat & LLM Integration](#core-chat--llm-integration)
  - [Prompt Engineering](#prompt-engineering)
  - [Structured Output](#structured-output)
  - [Chat Memory](#chat-memory)
  - [Tool Calling](#tool-calling)
  - [Retrieval-Augmented Generation (RAG)](#retrieval-augmented-generation-rag)
  - [Multimodality](#multimodality)
  - [Model Context Protocol (MCP)](#model-context-protocol-mcp)
  - [Observability & Optimization](#observability--optimization)
  - [Web Search](#web-search)
  - [Docker Model Runner](#docker-model-runner)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [HTTP Request Examples](#-http-request-examples)
- [Technologies & Topics](#-technologies--topics)


---

## 🌟 Overview

**Spring AI Integration** is a hands-on, modular reference repository for developers who want to learn, explore, and build AI-powered applications using the [Spring AI](https://docs.spring.io/spring-ai/reference/) framework on top of Spring Boot.

Each sub-project is a self-contained Spring Boot application that showcases a specific Spring AI feature or integration pattern. The modules range from beginner-friendly basic chat completions all the way to advanced topics like Model Context Protocol (MCP) security, financial RAG pipelines, prompt caching, observability metrics, and Docker-based local model execution.

### Why This Repository?

- **Multi-provider support** — OpenAI (GPT-4), Anthropic (Claude), and Ollama (local models) with easy provider swapping
- **Production-focused patterns** — advisors, memory management, vector stores, structured outputs, and prompt caching
- **MCP ecosystem** — full MCP server and client implementations across Stdio, WebFlux, and WebMVC transports
- **No vendor lock-in** — Spring AI's unified API abstracts away provider specifics
- **Learning-first design** — every module is focused, well-scoped, and independently runnable

---

## 📁 Repository Structure

```
Spring-AI-Integration/
│
├── spring-with-ai/                      # Introductory Spring AI basics
├── spring-ai-02-chat-with-llms/         # Chat with OpenAI (GPT-4)
├── spring-ai-03-chat-with-claude/       # Chat with Anthropic Claude
├── spring-ai-04-chat-with-ollama/       # Chat with local Ollama models
├── spring-ai-chat-options/              # Runtime chat configuration options
│
├── prompt-templates/                    # Prompt templating with variables
├── prompt-stuffing/                     # Prompt stuffing patterns
│
├── structured-output/                   # Bean/Map structured LLM outputs
├── native-structured-output/            # Native structured output (JSON mode)
│
├── chat-memory/                         # In-memory and persistent chat history
├── compacting-chat-memory-advisor/      # Memory compaction with advisors
│
├── tool-calling/                        # Function / tool calling integration
│
├── spring-ai-rag-vector-store/          # RAG with vector store (PGVector/simple)
├── spring-ai-financial-rag/             # Financial domain RAG pipeline
├── prompt-stuffing/                     # In-context document injection
│
├── multimodality/                       # Image + text multimodal inputs
│
├── mcp-server-stdio/                    # MCP Server via standard I/O
├── mcp-server-webflux/                  # MCP Server via WebFlux SSE
├── mcp-client-stdio/                    # MCP Client for stdio transport
├── spring-ai-mcp-client/                # Spring AI MCP client integration
├── spring-io-mcp-server/                # Spring.io MCP server example
├── spring-ai-mcp-elicitation/           # MCP elicitation patterns
├── spring-ai-mcp-security/              # Secured MCP with OAuth2/JWT
│
├── spring-ai-metrics/                   # Observability & Micrometer metrics
├── spring-ai-prompt-caching/            # Prompt caching for cost/latency
├── spring-ai-web-search/                # Web search tool integration
├── docker-model-runner/                 # Docker-based local model runner
│
└── http-requests.http                   # HTTP client sample requests
```

---

## 📦 Modules

### Core Chat & LLM Integration

#### `spring-with-ai`
The entry point into Spring AI. Demonstrates basic `ChatClient` usage, autoconfiguration, and simple request/response patterns. Ideal starting point for beginners.

#### `spring-ai-02-chat-with-llms`
Chat with **OpenAI GPT-4** using Spring AI's `ChatClient` API. Shows how to configure the OpenAI starter, send prompts, handle responses, and stream tokens.

**Key concepts:** `OpenAiChatModel`, `ChatClient`, `Flux<String>` streaming, system/user message roles.

#### `spring-ai-03-chat-with-claude`
Chat with **Anthropic Claude** (claude-3-5-sonnet / claude-3-haiku). Demonstrates Spring AI's Anthropic integration including prompt configuration and response handling.

**Key concepts:** `AnthropicChatModel`, multi-turn conversation, system prompt configuration.

#### `spring-ai-04-chat-with-ollama`
Chat with **locally running LLMs via Ollama** (e.g., Llama 3, Mistral, Phi-3). Zero cloud dependency — everything runs on your machine.

**Key concepts:** `OllamaChatModel`, local inference, Ollama Docker container setup.

#### `spring-ai-chat-options`
Demonstrates **runtime configuration** of chat parameters — temperature, top-p, max tokens, frequency penalty, etc. — both at startup and per-request level.

**Key concepts:** `ChatOptions`, `OpenAiChatOptions`, `OllamaChatOptions`, per-call overrides.

---

### Prompt Engineering

#### `prompt-templates`
Shows how to use **Spring AI's `PromptTemplate`** with parameterized variables, allowing dynamic prompt construction from templates and input maps.

**Key concepts:** `PromptTemplate`, `Message` types, variable interpolation, system vs. user templates.

#### `prompt-stuffing`
Demonstrates the **prompt stuffing** pattern — injecting external document content directly into the prompt context rather than using a vector store, useful for smaller documents or quick prototyping.

**Key concepts:** Document content injection, context window usage, in-context retrieval.

---

### Structured Output

#### `structured-output`
Shows how to extract **structured Java objects** (POJOs, records, Maps, Lists) from LLM responses using `BeanOutputConverter`, `MapOutputConverter`, and `ListOutputConverter`.

**Key concepts:** `OutputConverter`, `BeanOutputConverter<T>`, format instructions, JSON parsing.

#### `native-structured-output`
Uses **native JSON mode** (where supported by the provider) for guaranteed-valid JSON output from the LLM, bypassing prompt-based format instructions.

**Key concepts:** `responseFormat`, native JSON mode (OpenAI structured outputs), schema enforcement.

---

### Chat Memory

#### `chat-memory`
Implements **conversation memory** to maintain chat history across turns. Covers both in-memory (for development) and persistent storage strategies.

**Key concepts:** `MessageChatMemoryAdvisor`, `InMemoryChatMemory`, `ChatMemory`, `conversationId`.

#### `compacting-chat-memory-advisor`
Demonstrates how to handle **long conversations** using a memory compaction advisor that summarizes older messages when the context window limit approaches.

**Key concepts:** `AbstractChatMemoryAdvisor`, compaction strategy, token-aware summarization.

---

### Tool Calling

#### `tool-calling`
Full example of Spring AI's **function/tool calling** — registering Java methods as callable tools that the LLM can invoke during a conversation to fetch real-time data or execute logic.

**Key concepts:** `@Tool`, `FunctionCallback`, `FunctionCallbackWrapper`, tool registration, result handling.

```java
@Bean
public FunctionCallback weatherFunction() {
    return FunctionCallbackWrapper.builder(new WeatherService())
        .withName("getWeather")
        .withDescription("Get the current weather for a given city")
        .withInputType(WeatherRequest.class)
        .build();
}
```

---

### Retrieval-Augmented Generation (RAG)

#### `spring-ai-rag-vector-store`
Full **RAG pipeline** implementation: document ingestion, chunking, embedding generation, vector store persistence, and similarity-based retrieval at query time.

**Key concepts:** `VectorStore`, `SimpleVectorStore`, `TokenTextSplitter`, `EmbeddingModel`, `QuestionAnswerAdvisor`, document readers (PDF, text).

**Architecture:**
```
Document → Splitter → EmbeddingModel → VectorStore
                                            ↓
User Query → EmbeddingModel → Similarity Search → Retrieved Chunks
                                            ↓
                               ChatClient + Context → LLM → Answer
```

#### `spring-ai-financial-rag`
A domain-specific RAG application focused on **financial documents**. Ingests financial reports, filings, or market data and enables natural language Q&A over the content.

**Key concepts:** Domain-specific chunking strategies, finance-tuned prompts, retrieval confidence, source attribution.

---

### Multimodality

#### `multimodality`
Demonstrates **vision + text** multimodal capabilities — sending images alongside text prompts to multimodal models (e.g., GPT-4o, Claude 3, LLaVA via Ollama).

**Key concepts:** `UserMessage` with media attachments, `Media` type, image URL and base64 inputs, vision model configuration.

```java
UserMessage userMessage = new UserMessage(
    "Describe what you see in this image.",
    List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageResource))
);
```

---

### Model Context Protocol (MCP)

Spring AI Integration provides a comprehensive set of MCP modules covering server implementations, client integrations, security, and advanced patterns.

#### `mcp-server-stdio`
A **Stdio-transport MCP server** — communicates with the client via standard input/output streams. Ideal for local tool use with AI assistants like Claude Desktop.

**Key concepts:** `StdioServerTransport`, tool registration, MCP spec compliance.

#### `mcp-server-webflux`
A **reactive MCP server using WebFlux SSE** (Server-Sent Events) transport — suitable for HTTP-based, cloud-deployed MCP deployments.

**Key concepts:** `WebFluxSseServerTransport`, reactive streams, SSE endpoint, MCP tool exposure.

#### `mcp-client-stdio`
An **MCP client** that connects to a Stdio-based MCP server and invokes its registered tools through the Spring AI chat flow.

**Key concepts:** `StdioClientTransport`, `McpSyncClient`, tool discovery, function callback bridging.

#### `spring-ai-mcp-client`
A full **Spring AI MCP client** integration using the high-level Spring AI abstractions — connects to any MCP-compatible server and exposes its tools automatically to the `ChatClient`.

**Key concepts:** `McpFunctionCallback`, auto-tool-registration, Spring Boot autoconfiguration for MCP.

#### `spring-io-mcp-server`
An MCP server modeled after the Spring.io content structure — exposes tools for querying Spring ecosystem resources, projects, and documentation.

#### `spring-ai-mcp-elicitation`
Demonstrates the **MCP elicitation** pattern — the server proactively requests additional information from the user/client during a tool call.

**Key concepts:** Elicitation requests, dynamic input prompting, conversation-aware tool calls.

#### `spring-ai-mcp-security`
Implements **OAuth2 / JWT-secured MCP** — demonstrates how to protect MCP server endpoints with Spring Security, requiring proper bearer token authentication from MCP clients.

**Key concepts:** Spring Security OAuth2, JWT validation, `SecurityFilterChain`, protected tool endpoints.

---

### Observability & Optimization

#### `spring-ai-metrics`
Integrates **Micrometer observability** into Spring AI — tracking token usage, latency, model calls, and errors via meters and traces. Compatible with Prometheus, Grafana, and Zipkin.

**Key concepts:** `ObservationRegistry`, `ChatClientObservation`, custom metrics, Spring Boot Actuator, Micrometer.

#### `spring-ai-prompt-caching`
Demonstrates **prompt caching** (supported by Anthropic Claude and other providers) to reduce latency and API cost when the same system prompt or context is reused across requests.

**Key concepts:** Cache control headers, Anthropic cache_control API, cost optimization, cache hit/miss metrics.

---

### Web Search

#### `spring-ai-web-search`
Integrates **real-time web search** as a tool available to the LLM — allowing the model to fetch up-to-date information from the internet during a conversation.

**Key concepts:** Web search tool registration, search result injection, citation handling, Brave Search / Tavily integration.

---

### Docker Model Runner

#### `docker-model-runner`
Shows how to use **Docker's built-in Model Runner** (available in Docker Desktop 4.40+) to run LLMs locally via a Docker-native endpoint, bypassing the need for a separate Ollama installation.

**Key concepts:** Docker Model Runner endpoint, `spring.ai.openai.base-url` override, local model execution, zero-dependency local AI.

---

## 🔧 Prerequisites

| Requirement | Version | Notes |
|---|---|---|
| Java | 17+ | JDK 21 recommended |
| Maven | 3.8+ | Or use included `./mvnw` wrapper |
| Spring Boot | 3.x | Auto-configured via Spring AI starters |
| Spring AI | 1.x | See individual module `pom.xml` |
| Docker | 24+ | Required for vector DBs, Ollama, Model Runner |
| OpenAI API Key | — | Required for OpenAI modules |
| Anthropic API Key | — | Required for Claude modules |
| Ollama | Latest | Required for local model modules |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/drissiOmar98/Spring-AI-Integration.git
cd Spring-AI-Integration
```

### 2. Set Up API Keys

Create a `.env` file or export environment variables:

```bash
# OpenAI (GPT-4, embeddings)
export OPENAI_API_KEY=sk-your-openai-key

# Anthropic (Claude)
export ANTHROPIC_API_KEY=sk-ant-your-anthropic-key
```

### 3. Start Local Infrastructure (if needed)

For modules using Ollama:
```bash
docker run -d -p 11434:11434 --name ollama ollama/ollama
docker exec -it ollama ollama pull llama3
```

For modules using PGVector (RAG):
```bash
docker run -d \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=vectordb \
  -p 5432:5432 \
  pgvector/pgvector:pg16
```

### 4. Run a Module

Navigate to any module and start it:

```bash
cd spring-ai-02-chat-with-llms
./mvnw spring-boot:run
```

Or build and run the JAR:

```bash
./mvnw clean package -DskipTests
java -jar target/*.jar
```

---

## ⚙️ Configuration

Each module has its own `application.properties` or `application.yml`. Common configuration patterns:

```yaml
# OpenAI
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o
          temperature: 0.7

# Anthropic
spring:
  ai:
    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
      chat:
        options:
          model: claude-3-5-sonnet-20241022

# Ollama (local)
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3

# Vector Store (PGVector)
spring:
  ai:
    vectorstore:
      pgvector:
        index-type: HNSW
        distance-type: COSINE_DISTANCE
        dimensions: 1536
```

---

## 🌐 HTTP Request Examples

The root `http-requests.http` file contains ready-to-use REST client examples for all modules. These can be run directly in IntelliJ IDEA or VS Code with the REST Client extension.

```http
### Chat with OpenAI
POST http://localhost:8080/api/chat
Content-Type: application/json

{
  "message": "What is Spring AI?",
  "conversationId": "session-1"
}

### RAG Query
POST http://localhost:8080/api/rag/query
Content-Type: application/json

{
  "question": "What were the Q3 financial results?"
}

### Tool Calling
POST http://localhost:8080/api/chat/tools
Content-Type: application/json

{
  "message": "What is the weather like in Paris right now?"
}

### Multimodal (image + text)
POST http://localhost:8080/api/multimodal
Content-Type: application/json

{
  "message": "Describe this chart",
  "imageUrl": "https://example.com/chart.png"
}
```

---

## 🛠️ Technologies & Topics

| Category | Technologies |
|---|---|
| **Core Framework** | Spring Boot 3.x, Spring AI 1.x, Spring WebFlux |
| **LLM Providers** | OpenAI (GPT-4o), Anthropic (Claude 3.5), Ollama (Llama 3, Mistral, Phi-3) |
| **Vector Stores** | PGVector, SimpleVectorStore, In-Memory |
| **Embeddings** | OpenAI text-embedding-3-small/large, Ollama nomic-embed-text |
| **MCP** | Stdio, WebFlux SSE, WebMVC SSE transports |
| **Security** | Spring Security, OAuth2, JWT |
| **Observability** | Micrometer, Spring Boot Actuator, Prometheus, Zipkin |
| **Persistence** | PostgreSQL, JDBC Chat Memory |
| **Build** | Apache Maven, Spring Boot Maven Plugin |
| **Infrastructure** | Docker, Docker Model Runner, Docker Compose |

**GitHub Topics:** `java` `spring-ai` `springboot` `llms` `rag` `mcp` `mcp-server` `mcp-client` `mcp-security` `tool-calling` `prompt-engineering` `advisors` `multimodality` `structured-output` `vector-stores` `embedding` `open-ai` `ollama` `web-search` `docker-model-runner`

---


<p align="center">
  Built with ❤️ using <a href="https://docs.spring.io/spring-ai/reference/">Spring AI</a> — the portable, provider-agnostic AI framework for Java developers.
</p>
