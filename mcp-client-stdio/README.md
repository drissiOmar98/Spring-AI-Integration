# 🤖 MCP Client (STDIO) with Spring AI

This project demonstrates a **Spring Boot MCP STDIO client** that communicates with a **MCP server** (like `mcp-server-stdio`) and provides both:

- ✅ Programmatic access to tools via Spring AI `ChatClient`
- ✅ Web-based **AI Chat interface** for human interaction

It also integrates **Ollama LLM** for local model-based responses.

---

## 🚀 Features

- Communicates with MCP servers over **STDIO**
- Supports **multiple MCP server configurations** (`mcp-servers.json`)
- Uses **Spring AI ChatClient** to invoke tools
- Converts Markdown outputs to HTML with **MarkdownHelper**
- Web-based AI Chat interface with **Bootstrap + jQuery + AJAX**
- Debug logging for both custom packages and Spring AI client advisor

---

## 🛠️ Tech Stack

| Layer | Technology                  |
|-------|-----------------------------|
| Framework | Spring Boot 4               |
| AI / MCP | Spring AI  (MCP Client)     |
| Model | Ollama LLM (`gpt-oss:20b`)  |
| Markdown | CommonMark 0.24.0           |
| UI | HTML + Bootstrap 5 + jQuery |
| Java Version | 21+                         |

---

## 🧩 Architecture Overview

```text
+-----------------+       STDIO       +-------------------+
|                 |  <------------>   |                   |
| MCP Client      |                   | MCP Server        |
| (Spring Boot)   |                   | (Spring AI ) |
|-----------------|                   |------------------|
| ChatClient      |                   | VideoTools        |
| MarkdownHelper  |                   | ToolCallbacks     |
| REST Controller |                   |                   |
+-----------------+                   +-------------------+

Web UI (Browser) --AJAX--> REST Controller --ChatClient--> MCP Server
```
## 🗂️ Project Structure
```text
mcp-client-stdio/
│
├── src/main/java/com/omar/mcpclient/
│   ├── VideoController.java        # REST endpoint for AI Q&A
│   ├── MarkdownHelper.java         # Converts Markdown to HTML
│   └── Application.java            # Spring Boot entry point
│
├── src/main/resources/
│   ├── application.yml             # Client configuration
│   ├── mcp-servers.json            # STDIO server config
│   ├── static/
│   │   ├── index.html              # Web-based AI Chat interface
│   │   ├── robot.svg               # Avatar image
│   │   └── styles.css              # Optional custom styling
│
└── pom.xml  
```