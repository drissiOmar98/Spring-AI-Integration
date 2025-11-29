# 💬 Spring AI — Stateful Conversational Memory Chatbot

## Multi-Turn Dialogue • Cookie-Based Session Tracking • Persistent AI Memory

This project demonstrates how to build a **fully stateful AI chatbot** using **Spring AI**, featuring **persistent conversation memory**, **context-aware multi-turn dialogues**, and a clean **HTML/Bootstrap UI**.

The chatbot remembers previous interactions, maintains context across browser sessions, and logs AI interactions for monitoring.

---

## 🚀 Key Features

| Feature | Description |
|--------|-------------|
| **Persistent Conversation Memory** | Uses `MessageChatMemoryAdvisor` with a JDBC memory store to retain context across multiple requests and sessions. All previous messages are stored and retrievable for context-aware responses. |
| **Cookie-Based Session Tracking** | Each user receives a unique `X-CONV-ID` cookie. The chatbot uses this ID to link messages to the correct conversation, enabling persistent and seamless chat sessions. |
| **Context-Aware Multi-Turn Chat** | Every message sent by the user is processed with the full conversation history, allowing the AI to provide relevant and coherent responses. |
| **Logging Advisor** | Logs all inbound and outbound messages to assist with debugging, monitoring, and auditing conversations. |
| **Clean HTML/Bootstrap UI** | Frontend built with Thymeleaf and Bootstrap 5 for a modern, responsive chat interface. |
| **Secure Request/Response Models** | Uses Java `record` DTOs with validation for robust API design (`Input` and `Output`). |
| **Spring AI Integration** | Leverages `ChatClient` and customizable `Advisors` to build flexible AI-assisted workflows and memory management. |

---

## 📦 Technologies Used

- **Spring Boot 3.3+**
- **Spring AI**
    - `ChatClient` for interacting with AI models
    - `Advisors` for memory, logging, and custom behaviors
    - `MessageChatMemoryAdvisor` for persistent conversation context
- **JDBC Memory Store** for persistent storage of conversations
- **Thymeleaf** templates for server-side rendering
- **HTML + Bootstrap 5** for frontend chat UI
- **Java Records** for structured, type-safe API requests/responses
- **Cookie-Based Session Management** (`X-CONV-ID`)

---

## 🧠 How Chat Memory Works

1. **Conversation Tracking:**  
   Each user interaction generates or reuses a unique conversation ID stored in a browser cookie (`X-CONV-ID`).

2. **Persistent Memory:**  
   Messages are automatically saved in a JDBC store through `MessageChatMemoryAdvisor`. This ensures context is preserved across sessions and server restarts.

3. **Context-Aware Responses:**  
   The `ChatClient` retrieves past messages for the conversation and includes them when generating new responses, allowing the AI to maintain coherent multi-turn dialogues.

4. **Logging & Monitoring:**  
   `SimpleLoggerAdvisor` captures every inbound prompt and AI response, making it easy to debug or audit interactions.

---

## 🖥️ Frontend Features

- Clean and responsive chat interface
- Supports multi-turn conversations
- Displays AI responses in real-time
- Integrated with cookie-based session tracking for consistent chat context


---

## ⚙️ Configuration

Configure your AI provider and API key using environment variables.  
Example for OpenAI:

```bash
export OPENAI_API_KEY=YOUR_TOKEN_VALUE_HERE
```

---

## ⚡ Example API Usage

### Chat Endpoint
```http
POST http://localhost:8080/api/chat
Content-Type: application/json
Cookie: X-CONV-ID=<your-conversation-id>

{
  "prompt": "Hello! Can you summarize Spring AI for me?"
}

