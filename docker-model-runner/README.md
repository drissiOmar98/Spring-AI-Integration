# 🐳🤖 Docker Model Runner with Spring AI

![Docker + Spring AI](https://img.shields.io/badge/Docker-Spring%20AI-blue)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-green)
![Local AI](https://img.shields.io/badge/AI-Local%20Models-success)

## 🚀 Run AI Models Locally with Docker & Spring Boot

This project demonstrates how to **run AI models locally** using **Docker Desktop Model Runner** and integrate them seamlessly with **Spring AI**.

No OpenAI keys.  
No cloud dependency.  
🔒 **Your data stays local.**

Spring AI connects to Docker’s **OpenAI-compatible API**, giving you the same developer experience while keeping **full control over models, data, and infrastructure**.

---

## 🧠 What This Project Shows

This application showcases:

- 🐳 **Docker Desktop Model Runner (Beta)**
- 🌱 **Spring Boot 3.5.x**
- 🤖 **Spring AI abstraction layer**
- ☕ **Java 21**
- 🔌 **OpenAI-compatible local inference**

The app sends prompts to locally running models via Docker and prints the AI response using Spring AI’s `ChatClient`.

---

## ✨ Key Features

| Feature | Description |
|------|------------|
| 🧠 Local AI Inference | Run LLMs locally without cloud APIs |
| 🔌 OpenAI-Compatible API | Works seamlessly with Spring AI |
| 🐳 Docker Model Runner | Easy model management via Docker Desktop |
| 🔒 No API Keys Required | Fully offline & privacy-friendly |
| ⚡ Fast Iteration | Ideal for local development & experimentation |
| 🧩 Extensible | Easily swap models or add RAG, streaming, tools |

---

## 🧰 Requirements

Before running the project, make sure you have:

- 🐳 **Docker Desktop 4.40+**
    - Model Runner enabled
    - Apple Silicon (M1 / M2 / M3 recommended)
- ☕ **Java 24 JDK**
- 📦 **Maven**
- 🧠 Enough **RAM** for AI models (varies by model)

---

## 📦 Dependencies

Core dependencies used in this project:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```
---

## ⚡ Quickstart

### 🛠️ 1. Enable Docker Model Runner
1. 🐳 Open **Docker Desktop** → **Settings** → **Features in development (Beta)**
2. ✅ Enable **Docker Model Runner**
3. 🌐 Enable **Host-side TCP support** (default port: `12434`)
4. 🔁 Apply changes & **restart Docker Desktop**

### 2. Pull a model
Example:
```bash
docker model pull ai/gemma3
```
List models:
```bash
docker model list
```

> Tip: Use smaller models if you are low on RAM.

### 3. Configure the application
Example `application.properties`:
```properties
# No real API key needed for local Model Runner
spring.ai.openai.api-key=_
# Docker Model Runner base URL (host-side TCP)
spring.ai.openai.chat.base-url=http://localhost:12434/engines/llama.cpp
# Default model to use (must match a pulled model)
spring.ai.openai.chat.options.model=ai/gemma3
```

### 4. Build & run
Build:
```bash
./mvnw clean package
```
Run:
```bash
./mvnw spring-boot:run
```
The app will start and (if configured) run a sample `CommandLineRunner` that prompts the local model and logs the response.
