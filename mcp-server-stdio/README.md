# 🎬 MCP Server (STDIO) with Spring AI

This project demonstrates how to build a **MCP (Model Context Protocol) server**  
that communicates over **STDIO**, using **Spring AI**. It includes a working example with **Video tools** backed by a JPA repository.

---

## 🚀 What is MCP STDIO?

MCP servers using STDIO are commonly used for:
- 🤖 Local AI agents
- 🛠 IDE integrations
- 📦 Tooling that communicates via **standard input/output**
- 🔒 Secure, process-based AI interactions (no HTTP required)

STDIO transport allows AI clients (like **Cursor** or **Claude**) to communicate directly with local tools without network overhead.

---

## 🧩 Project Features

- ✅ Spring Boot 4 + Java 21+
- ✅ MCP server over **STDIO**
- ✅ Clean STDOUT handling (required for MCP)
- ✅ JPA persistence layer with **H2** for demo purposes
- ✅ Video tools registered as MCP tools:
    - `get_all_videos` – returns all videos
    - `search_videos` – search videos by title
    - `get_video_by_title` – get a single video by exact title
- ✅ Structured, professional logging to **file** (STDOUT reserved for MCP)
- ✅ Fully annotated with `@Tool` for Spring AI MCP

---

## 🛠️ Tech Stack

| Layer | Technology              |
|-------|-------------------------|
| Framework | Spring Boot 4           |
| AI / MCP | Spring AI  (MCP server) |
| Persistence | JPA + H2 Database       |
| Logging | SLF4J + Logback         |
| Java Version | 21+                     |

---