# 🌿 Spring AI - 05
## Prompt Templates & AI-Powered Blog Post Generator + Banking Chat Assistant

This project showcases how to build **dynamic, reusable, and structured AI prompts** using **Spring AI**.  
It includes:

- An `ArticleController` that generates complete, ready-to-publish blog posts based on a topic.
- An `AcmeBankController` that demonstrates how to build a **domain-restricted AI chatbot** for banking customer service, using strict system instructions.

These examples highlight the power of prompt templating, system messages, and Spring AI’s ChatClient.

---

## ✨ Key Features

### 📝 AI Blog Post Generator
- **Flexible Prompt Templates**  
  Build reusable prompts using placeholders and parameters for structured AI interactions.

- **AI Blog Post Generator (`ArticleController`)**  
  Automatically creates polished ~500-word articles with headings, sections, and clean formatting.

- **System & User Messages**  
  Define tone, structure, and output formatting for predictable and controlled AI responses.

- **Topic Customization**  
  Provide a simple `?topic=` query parameter to instantly generate a full article on any subject.

- **Publication-Ready Output**  
  Every response includes a title, introduction, body, conclusion, and clear markdown structure.

### 🏦 Banking Chat Assistant (`AcmeBankController`)
- **Strict Domain Rules**  
  The assistant can only answer questions about:
    - Account balances
    - Transactions
    - Branch locations & hours
    - General banking services

- **Off-Topic Handling**  
  For anything unrelated, it responds with:  
  **"I can only help with banking-related questions."**

- **Logging with Advisors**  
  Uses `SimpleLoggerAdvisor` to log prompts and responses for monitoring/debugging.

- **Clean ChatClient Builder Usage**  
  Demonstrates customizing the ChatClient through constructor injection.

---

## 📋 Requirements

Ensure you have the following installed:

- **Java 21+**
- **Maven or Gradle**
- **API key** for your chosen AI provider  
  (OpenAI-compatible LLMs or local model via Spring AI)

---

## ⚙️ Configuration

Configure your AI provider and API key using environment variables.  
Example for OpenAI:

```bash
export OPENAI_API_KEY=YOUR_TOKEN_VALUE_HERE
