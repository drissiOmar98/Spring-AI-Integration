# Spring AI Chat Options Demo

A Spring Boot application demonstrating how to use various OpenAI chat options with Spring AI.

## Prerequisites

- Java 24
- Maven
- OpenAI API key

## Setup

1. Set your OpenAI API key as an environment variable:
   ```bash
   export OPENAI_API_KEY=your_api_key_here
   ```

2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Endpoints

### `/` - Basic Chat Options
- **Model**: GPT-5
- **Temperature**: 1.0 (creative responses)
- Returns full ChatResponse object

### `/creative` - Creative Writing
- **Model**: gpt-4o-mini  
- **Temperature**: 1.0 (maximum creativity)
- **Presence Penalty**: 0.6 (encourages topic diversity)
- **Max Tokens**: 150
- Returns content string

### `/facts` - Factual Responses
- **Model**: gpt-4o-mini
- **Temperature**: 0.1 (focused, deterministic)
- **Frequency Penalty**: 0.0 (no repetition penalty)
- **Max Tokens**: 50
- Returns content string

### `/code` - Code Generation
- **Model**: gpt-4o-mini
- **Temperature**: 0.3 (balanced creativity/consistency)
- **Max Tokens**: 200
- **Stop Sequences**: `["END_CODE", "\n\n---"]`
- Returns content string

## Key Features

This demo showcases Spring AI's `OpenAiChatOptions` builder for configuring:
- Model selection
- Temperature control
- Token limits
- Penalty adjustments
- Stop sequences
- Response formatting options