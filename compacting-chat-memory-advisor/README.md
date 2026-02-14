# 🧠 Spring AI Compacting Chat Memory Advisor

Ever wondered what happens when your chat memory fills up in Spring AI?  
This project demonstrates a **custom chat memory advisor** that automatically **compacts older conversation messages**, preserving important context while saving tokens and memory. Inspired by production needs and real conference questions, this implementation shows how to efficiently manage chat context in Spring Boot applications.

---
## ❌ The Problem

Traditional chat memory strategies (like `MessageChatMemoryAdvisor`) use a **sliding window** approach:  
when the conversation exceeds a set number of messages, the **oldest messages are simply discarded**. This can create several issues:

- ⚠️ **Context Loss** – Important details from earlier messages are permanently gone
- 🔄 **Broken References** – The AI may forget or misinterpret prior information
- 👎 **Poor User Experience** – Users may need to repeat themselves, frustrating interactions

---

## 💡 The Solution

`CompactingChatMemoryAdvisor` solves these problems by **automatically summarizing old messages** instead of dropping them.

When your conversation approaches the configured threshold, the advisor:

1. 📝 Collects the oldest messages (e.g., first 40 messages)
2. 🤖 Uses an LLM to generate a concise summary that preserves **key information**
3. 🔄 Replaces the selected messages with a **single summary message**
4. 🧩 Keeps recent messages intact to maintain **full context**

**✅ Result:** Your conversation stays within memory limits **while retaining important context**, ensuring more coherent, cost-efficient, and user-friendly AI interactions.


## 🌐 Project Overview

The Compacting Chat Memory Advisor demonstrates advanced memory management in Spring AI chat applications.  
Instead of dropping old messages when memory limits are reached, this advisor automatically **summarizes old conversation history**, preserving context while optimizing token usage.

**Key Goals:**
- Reduce token usage and API costs
- Preserve important context for long conversations
- Support production-ready chat applications with efficient memory


## 🌟 Features

- **Automatic message compaction**: Summarizes oldest messages when the conversation reaches a threshold.
- **Dual memory support**: Compare **regular memory** (drops old messages) vs **compacting memory** (summarizes old messages).
- **Customizable thresholds**: Configure max messages, compaction trigger, and number of messages to summarize.
- **Multi-model setup**: Use **OpenAI GPT-5** for main responses and **Google Gemini 2.5 Flash** for cost-effective summarization.
- **Debug logging**: Track token usage, compaction events, and conversation stats.

---

## 📊 Regular Memory vs Compacting Memory

| Feature | Regular Memory | Compacting Memory |
|---------|----------------|-----------------|
| Message retention | Drops oldest messages when limit reached | Summarizes oldest messages into a single summary |
| Token usage | Can spike as conversation grows | Optimized by summarizing old messages |
| Context preservation | Only recent messages are kept | Key context preserved in summaries |
| Automatic compaction | ❌ | ✅ |
| Manual compaction | ❌ | ✅ |
| Suitable for | Short, stateless chats | Long multi-turn conversations, support, coding sessions |

## ⚙️ Configuration

Configure memory behavior in `application.yml` under `compact.memory`:

```yaml
compact:
  memory:
    max-messages: 20
    compact-threshold: 15
    messages-to-compact: 8
```

## Prerequisites

- Java 17+
- Spring Boot 3.x
- OpenAI API key
- Google GenAI (Gemini) API key 

## 🎯 Use Cases

### 🛠️ Customer Support Chatbot
Long customer conversations can easily span **dozens of messages**.  
`CompactingChatMemoryAdvisor` preserves **early context** (customer details, initial issues) while keeping the conversation **within token limits**, ensuring smoother interactions.

### 💻 Code Generation Assistant
Multi-turn programming sessions often require remembering **earlier design decisions**.  
Automatic summarization maintains the context of architectural choices **without bloating prompts**, improving coding efficiency and AI coherence.

### 📚 Research Assistant
Extended Q&A sessions benefit from retaining **thematic continuity**.  
Compaction keeps the conversation focused on the topic **without repeating every detail**, enabling deeper and more productive discussions.


## API Endpoints

### `/compact/memory` (GET)
Chat with compacting memory enabled.

**Parameters:**
- `message` (required): Your message to the AI

**Example:**
```bash
curl "http://localhost:8080/compact/memory?message=Tell+me+about+Spring+AI"
```

### `/compact/trigger` (GET)
Manually trigger compaction of conversation history.

**Returns:** Summary of what was compacted (message counts, token savings)

**Example:**
```bash
curl http://localhost:8080/compact/trigger
```

**Response:**
```
Compacted 40 messages into summary. Messages: 80 -> 41, Tokens: 8234 -> 4521 (saved 3713 tokens)
```

### `/compact/clear` (GET)
Clear the conversation history and start fresh.

**Example:**
```bash
curl http://localhost:8080/compact/clear
```

### `/memory` (GET)
Standard chat endpoint using Spring AI's default `MessageChatMemoryAdvisor` (for comparison).

**Example:**
```bash
curl "http://localhost:8080/memory?message=Hello"
```