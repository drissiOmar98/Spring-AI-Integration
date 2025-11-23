# Spring AI Recipe Project

A Spring Boot application demonstrating AI-powered recipe suggestions using Spring AI and large language models (LLMs).  
The project provides multiple REST endpoints to generate recipes dynamically, with support for general, seafood-specific, and ingredient-based recommendations.

---

## 🚀 Features

- **RecipesController** – General AI recipe suggestions.
- **SeaFoodRecipeController** – AI-powered seafood recipes with system constraints.
- **RecipeChefController** – Context-aware AI “Chef Assistant” maintaining conversation history.
- **RecipeSuggesterController** – Flexible endpoints:
    - List of dishes containing an ingredient
    - Country → Dish mapping
    - Single best recipe object
    - List of best recipes with details

- Integration with **Spring AI ChatClient** (OpenAI-compatible LLMs, Gemini, Groq, DeepSeek, OpenRouter, Docker Model Runner).
- Logging of AI interactions using **SimpleLoggerAdvisor**.
- Uses **PromptTemplate** and output converters for typed and structured responses.

---

## 📦 API Endpoints

| Controller | Endpoint | Description |
|------------|----------|-------------|
| RecipesController | `/recipes/suggest-recipe` | General recipe suggestions using AI. |
| SeaFoodRecipeController | `/recipes/sea-food/suggest-recipe` | Seafood-only recipe suggestions, off-topic queries return "I don't know". |
| RecipeChefController | `/recipes/chef/suggest-recipe` | Contextual seafood recipe suggestions with persistent conversation history. |
| RecipeSuggesterController | `/recipes/suggester` | List of dishes containing an ingredient (`List<String>`). |
| RecipeSuggesterController | `/recipes/suggester/country` | Dishes by country (`Map<String,Object>`). |
| RecipeSuggesterController | `/recipes/suggester/best` | Single best recipe (`Recipe` object). |
| RecipeSuggesterController | `/recipes/suggester/best-list` | List of top recipes (`List<Recipe>`). |

---

## ⚙️ Configuration

Spring profiles are used for different AI providers.

### 🌟 OpenAI Config
- Create an API Key: [OpenAI API Key](https://platform.openai.com/account/api-keys)
- Set environment variable:
  ```bash
  export OPENAI_API_KEY=YOUR_TOKEN_VALUE_HERE

## 🔧 Requirements
- Java 21+
- Spring Boot 3.x
- Docker (for local LLM models)
- OpenAI-compatible LLMs (or other supported providers)

## 💡 Notes
- Controllers use **Spring AI ChatClient** for LLM communication.
- `SimpleLoggerAdvisor` logs prompts and responses for debugging.
- PromptTemplates and OutputConverters ensure structured, typed responses.
- RecipeChefController maintains **conversation history** for context-aware suggestions.
