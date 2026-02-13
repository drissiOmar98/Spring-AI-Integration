# 💸 Spring AI Prompt Caching — Stop Wasting Money on Repeated Tokens

Tired of watching your AI API costs skyrocket?  
**Prompt caching** can reduce your Anthropic Claude costs by **up to 90%** by caching repeated content like system prompts and tool definitions.

This project demonstrates **how to implement prompt caching in Spring AI** using **Anthropic Claude models**, allowing you to reuse expensive system prompts instead of sending them on every request.

By the end of this demo, you’ll have a **working Spring Boot application** that proves prompt caching is enabled — and shows exactly how many tokens you saved 💰

---

## 🧠 What Is Prompt Caching?

Prompt caching allows AI models to **reuse previously sent content** instead of reprocessing it every time.

### ❌ Without Prompt Caching
- System prompts are sent on every request
- Large prompts = high token usage
- API costs increase rapidly

### ✅ With Prompt Caching
- System prompts are cached once
- Only dynamic user input is sent
- Massive reduction in input tokens
- Faster responses & lower bills

Anthropic Claude supports **native prompt caching**, making it ideal for production AI systems with:
- Large system prompts
- Tool definitions
- Repeated instructions

---

## 🎯 What This Project Demonstrates

✅ How the **context window** works in Anthropic models  
✅ What content can be cached (system messages)  
✅ Which Claude models support prompt caching  
✅ How to enable prompt caching in **Spring AI**  
✅ How to use the **SYSTEM_ONLY caching strategy**  
✅ How to log cache creation & cache read tokens  
✅ How to verify that caching is actually working

---

## ⚙️ Tech Stack

- ☕ Java 21+
- 🌱 Spring Boot 4.x
- 🤖 Spring AI 2
- 🧠 Anthropic Claude (Sonnet 4.5)
- 🔐 Environment-based API key configuration

## 🧠 How the Context Window Really Works

Every request sent to an LLM is evaluated within a **context window** — a limited number of tokens that includes:

- System messages
- User messages
- Tool definitions
- Previous conversation history

Without prompt caching, **all of this content is re-sent on every request**, even if nothing changed.

Prompt caching optimizes this by allowing models like Anthropic Claude to:
- Store reusable parts of the prompt
- Reference them by cache ID
- Skip reprocessing identical content

This reduces:
- Token usage
- Latency
- Cost per request

Understanding the context window is critical for building scalable AI applications.

## 💰 Why Prompt Caching Saves So Much Money

Prompt caching dramatically reduces **input token costs**, which are often the largest expense in AI applications.

Typical high-cost components:
- Large system prompts (instructions, policies, tone)
- Tool schemas and function definitions
- Repeated agent instructions

With prompt caching:
- System prompts are paid for **once**
- Every subsequent request reuses cached tokens
- Costs scale with **user input only**

In real-world production systems, this can result in:
- 🔻 70–90% reduction in input tokens
- 🔻 Lower latency
- 🔻 Predictable billing


## 🧪 Cache Lifecycle Explained

Prompt caching follows a simple lifecycle:

1. **Cache Creation**
    - The system prompt is sent for the first time
    - Tokens are billed normally
    - Claude stores the content in cache

2. **Cache Read**
    - The same system prompt is reused
    - Tokens are read from cache
    - Input token cost is dramatically reduced

3. **Cache Invalidation**
    - If the system prompt changes
    - A new cache entry is created
    - Old cache entries expire automatically

Spring AI handles this seamlessly when using
`AnthropicCacheStrategy.SYSTEM_ONLY`.


## 🧩 Real-World Use Cases

Prompt caching is ideal for:

- AI agents with complex instructions
- Chatbots with strict system rules
- Content generation pipelines
- Tool-heavy LLM applications
- Enterprise AI workflows

Any system with **repeated instructions** will benefit.


## 📈 Monitoring & Observability

Prompt caching is only useful if you can verify it’s working.

This project logs:
- Cache creation input tokens
- Cache read input tokens

In production, you should:
- Export metrics to Prometheus
- Track average input tokens per request
- Alert when cache reads drop unexpectedly

Observability ensures your cost savings persist over time.
