# Spring AI - Chat with Anthropic Claude Models

This project demonstrates how to integrate **Spring AI** with **Anthropic Claude** LLMs.  
It contains a clean, modular REST controller for sending prompts to Claude models, and integration tests to verify chat functionality.

---

## 🚀 Features

- Chat with **Anthropic Claude** using Spring AI ChatClient.
- Non-streaming POST endpoint for sending prompts and receiving full responses.
- Fully configured for modular use in Spring Boot.
- **Integration tests** using MockMvc to verify chat endpoints.

---

## 📦 Prerequisites

Before running this project, make sure you have:

- **Java 21+**
- **Maven or Gradle**
- API Key for Anthropic Claude

---

## 🔑 Anthropic Claude Config

- Create an API Key: [Anthropic Console](https://console.anthropic.com/settings/keys)
- Set the environment variable:
  ```bash
  export ANTHROPIC_API_KEY=YOUR_TOKEN_VALUE_HERE
