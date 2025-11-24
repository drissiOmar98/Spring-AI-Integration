# Spring AI - 02 - Chat with OpenAI-Compatible LLMs

This project demonstrates how to integrate **Spring AI** with multiple **OpenAI-compatible Large Language Models (LLMs)** including Gemini, Groq, DeepSeek, OpenRouter, and Docker Model Runner.  
It contains clean configuration examples, environment variable setups, and YAML-based profiles to switch between LLM providers easily & integration tests to verify AI chat functionality across providers.


---
## 🚀 Features

- Chat with **Gemini** using OpenAI compatibility mode
- Chat with **Groq** models (Llama 3.3, Mixtral, etc.)
- Chat with **DeepSeek** (DeepSeek Reasoner, V3 Chat, etc.)
- Chat using **OpenRouter** (free and paid models)
- Run local models with **Docker Model Runner** (Smollm2, Llama, etc.)
- Fully configured `application-*.yml` files
- Modular structure for each provider
- **StreamController** providing both full-response and streaming (SSE) endpoints for real-time or one-shot AI responses
- **Integration tests** with MockMvc for ChatController across multiple profiles (Gemini, Groq, OpenRouter, DMR)

---
### 💬 StreamController – Streaming and Full Response Endpoints

- **Full Response Endpoint (`/api/stream/full`)**  
  Returns the complete AI-generated response in one request (non-streaming).  
  Useful for traditional REST API usage where the entire output is needed at once.

- **Streaming SSE Endpoint (`/api/stream/sse`)**  
  Streams the AI response token-by-token, ideal for real-time UIs or live feedback applications.  
  Allows clients to render partial outputs as they arrive, improving interactivity and responsiveness.

- Works with the same **ChatClient** abstraction as `ChatController` and fully compatible with all configured LLM profiles (Gemini, Groq, DeepSeek, OpenRouter, DMR).

- Supports query parameters to customize the prompt for both endpoints, making it flexible for dynamic use cases.


---

## 📦 Prerequisites

Before running this project, make sure you have:

- **Java 21+**
- **Maven or Gradle**
- **Docker Desktop** (required only for Docker Model Runner)
- API Keys for the providers you want to use

---

## 🔑 API Key Configuration

Set the following environment variables depending on the LLM provider.

### 🌟 Gemini Config
- Create an API Key: https://aistudio.google.com/app/apikey
- Set the environment variable:
  ```bash
  GEMINI_API_KEY=YOUR_TOKEN_VALUE_HERE

### ⚡ Groq Config
- Create an API Key: https://console.groq.com/keys
- Set the environment variable:
  ```bash
  GROQ_API_KEY=YOUR_TOKEN_VALUE_HERE

### 🤖 DeepSeek Config
- Create an API Key: https://platform.deepseek.com/api_keys
- Set the environment variable:
  ```bash
  DEEPSEEK_API_KEY=YOUR_TOKEN_VALUE_HERE

### 🌍 OpenRouter Config
- Create an API Key: https://openrouter.ai/settings/keys
- Set the environment variable:
  ```bash
  OPENROUTER_API_KEY=YOUR_TOKEN_VALUE_HERE

### 🐳 Docker Model Runner
- Install Docker Desktop
- Follow the official setup guide to install Docker Model Runner:
  https://docs.docker.com/ai/model-runner/