# 🌐 Web Search with Spring AI

Ever asked your AI about recent events and got "As of my last update..."?  
This project solves that problem by integrating **real-time web search** with Spring AI, enabling Java applications to access up-to-date information directly from the web using OpenAI models.

Spring AI's **web search capabilities** allow LLMs to retrieve fresh data from search engines, official blogs, news sites, and other trusted sources. This project demonstrates how to leverage **OpenAI GPT-4o search-preview** and **GPT-5** models for live data queries.

---

## 🧠 What is Web Search in AI?

Web search with AI is a technique that allows large language models (LLMs) to **query live web data** during prompt execution, instead of relying solely on their training data.

- 🔹 **Traditional LLM responses** are limited to pre-trained knowledge ("As of my last update…")
- 🔹 **Web Search integration** enables LLMs to pull fresh data in real-time
- 🔹 **Spring AI** provides a structured way to configure web search using:
    - `WebSearchOptions` → Context size, user location, and filters
    - `OpenAiChatOptions` → Model options and web search integration
    - `ChatClient` → Sends prompts and retrieves AI responses

Reference: [OpenAI Web Search Guide](https://platform.openai.com/docs/guides/tools-web-search)

## 🚀 Features

- 🤖 **GPT-5 Web Search**: Get interesting facts from GPT-5 with real-time web access
- 📰 **Spring Ecosystem News**: Curated news summaries from official blogs, releases, and tech publications
- 🍽️ **Restaurant Finder**: Exactly 3 location-aware restaurant recommendations based on cuisine, price, and city
- 🌐 **Real-Time Web Search**: Avoid outdated AI responses
- ⚙️ **Location Awareness**: Customize queries based on city, region, country, and timezone
- 🛠️ **Custom Prompts**: Structured system and user prompts for reliable, type-safe responses

---

## 🧰 Tech Stack & Tools

- Java 21+
- Spring Boot 4.x
- Spring AI 2.0.0-M2


---

## ⚡ Endpoints Overview

### 1. 🟢 GPT-5 Web Search

- **Path:** `/gpt5`
- **Purpose:** Retrieve a fun, real-time fact about GPT-5
- **Method:** GET
- **Usage:** No parameters required
- **Response:** AI-generated text from GPT-5

### 2. 🟢 Spring Ecosystem News

- **Path:** `/news`
- **Purpose:** Curates latest news from the Spring ecosystem
- **Method:** GET
- **Behavior:** Checks the last 7 days by default, expands to 1 month if limited news
- **Response:** Text summary including:
    - Date & source
    - Impact level
    - Brief summary
    - Developer action items

### 3. 🟢 Restaurant Finder

- **Path:** `/restaurants`
- **Purpose:** Returns exactly 3 restaurant recommendations
- **Method:** GET
- **Query Parameters (optional):**
    - `cuisine` (default: `any`)
    - `priceRange` (default: `$`)
    - `city` (default: `Cleveland`)
    - `region` (default: `Ohio`)
    - `country` (default: `US`)
    - `timezone` (default: `America/New_York`)
- **Response:** Exactly 3 restaurants with:
    - Name & neighborhood
    - Specialty
    - Price & rating
    - Status & contact info

---
