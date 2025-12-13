# 🌍 Spring AI + Ollama — AI Function Calling with Real-Time Weather Integration

## Conversational AI • Local LLM (Ollama) • Tool / Function Calling • Real-Time Weather Data

This project demonstrates how to build an **AI-powered Spring Boot application** using **Spring AI** and **Ollama** that can:

- 💬 Answer **natural language questions about cities**
- 🌤️ **Automatically fetch real-time weather data** when relevant
- 🔧 Use **Spring AI Function Calling** to connect LLMs with external APIs
- ⚡ Run efficiently with **Java 21 Virtual Threads**
- 🧠 Execute fully **locally** using **Ollama (LLaMA 3.x)**

The AI decides *when* to call the weather API — no hardcoded logic, just intelligent tool usage.

---



## 🚀 What This Project Showcases

- **Spring AI + Ollama integration**
- **Function calling (AI tools)** with type-safe Java records
- **External REST API integration** (WeatherAPI.com)
- **Context-aware AI responses**
- **Clean, production-ready architecture**
- **Local-first AI (no cloud dependency)**

---

## ⚙️ How It Works

This application connects an **AI Chat Client (Ollama)** with a **Spring AI function** that the model can invoke automatically whenever **weather information is required**.

### 🔄 High-Level Execution Flow

1. 🌐 **Client Request**  
   The client calls the REST endpoint:  
   `GET /cities?message=...`

2. 🤖 **AI Request Processing**  
   The controller forwards the user’s natural language query to the configured **Spring AI `ChatClient`**.

3. 🧠 **Intent Analysis & Tool Selection**  
   The AI model analyzes the request.
    - If weather data would improve the response, it **automatically triggers** the registered tool:  
      `currentWeatherFunction`.

4. 🌡️ **External Weather API Call**  
   The function is implemented by **`WeatherService`**, which:
    - Calls the external Weather API endpoint: `/current.json`
    - Uses `RestClient` for HTTP communication
    - Returns a **structured, type-safe response**.

5. ✨ **Context-Enriched AI Response**  
   The AI receives the function result and composes a **natural, user-friendly reply** that includes **live weather data** alongside its reasoning.

> 🧩 This flow demonstrates **true AI tool calling**:  
> the model decides *when* and *how* to invoke external services—no manual routing required.





## 🧰 Prerequisites

- ☕ **Java 21+** — required for **virtual threads** support
- 📦 **Maven or Gradle** — for dependency management and builds
- 🦙 **Ollama Server (Local or Remote)** — if using Ollama as the LLM provider
    - Make sure the Ollama service is running and accessible
- 🌤️ **WeatherAPI.com API Key** (or a compatible weather provider)
- 🌐 **Network Access**
    - Access to the Ollama **base URL**
    - Access to the **Weather API** endpoint  

---

## Configuration

Below is an example `application.yml` fragment used by this project. Copy and paste into your application.yml or application-*.yml.

```yaml
spring:
  application:
    name: 03-chat-ollama

  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3.2          # Ollama model you are using
          temperature: 0.7

  threads:
    virtual:
      enabled: true              # Enable Java 21 virtual threads for high-concurrency

logging:
  level:
    org.springframework.ai.chat.client.advisor: DEBUG

weather:
  api-key: ${WEATHER_API_KEY}   # Externalized API key from environment variable
  api-url: https://api.weatherapi.com/v1
```




