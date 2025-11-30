# 🔍 Spring AI — RAG (Retrieval-Augmented Generation) Vector Store

## Semantic Search • Persistent Embeddings • Context-Aware AI Responses

This project demonstrates how to implement **Retrieval-Augmented Generation (RAG)** using **Spring AI**.  
It features a **persistent vector store** for semantic search and document retrieval, enabling AI models to provide **context-aware, accurate responses** based on preloaded documents.

The project is ideal for developers looking to build:

- Knowledge-based AI assistants
- Context-rich AI model querying systems
- Semantic search engines
- Technical documentation retrieval APIs

---

## 🚀 Key Features

| Feature | Description |
|---------|-------------|
| **Persistent Vector Store** | Stores embeddings in `vectorstore.json` for reuse across application restarts |
| **Automatic Document Processing** | Text is chunked, embedded, and saved automatically if vector store does not exist |
| **Semantic Search** | Vector-based retrieval enables context-aware AI responses |
| **RAG-Enabled ChatClient** | Uses `QuestionAnswerAdvisor` to enrich AI answers with vector store context |
| **Structured JSON Responses** | Endpoints return typed Java objects (`Models`) for easy API consumption |
| **Default & Custom Queries** | Supports default queries and user-provided queries for AI model information |
| **Performance Optimized** | Vector store is loaded once at startup; subsequent queries are fast |
| **Extensible** | Easy to add more documents or support multiple embedding models |

---

## 📦 Prerequisites

Before running the project, ensure you have:

- **Java 21+**
- **Maven or Gradle**
- **Spring Boot 3.3+**
- Spring AI dependency:

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-advisors-vector-store</artifactId>
</dependency>
