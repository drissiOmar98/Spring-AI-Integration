# 🚀 Native Structured Output in Spring AI

This demo project showcases **Spring AI 1.1's Native Structured Output** feature,  
which enables **reliable, type-safe extraction** of structured data from AI model responses. 🤖📊

Instead of working with unpredictable text output, this project demonstrates how to
build **robust, production-ready AI applications** using strongly typed Java records. ☕✅

By sending your JSON schema directly to the model API, Spring AI ensures that
LLM responses always match your expected structure — eliminating parsing errors
and improving overall reliability. 🔒✨

## What is Native Structured Output?

Starting with Spring AI 1.1, you can use **native structured output** through model-specific APIs rather than 
prompt-based formatting. This means:

- JSON schemas are sent directly to the AI provider's structured output API
- The AI model **guarantees** output conforming to your schema
- No need to append format instructions to prompts
- More reliable results than the previous prompt-based approach

## Supported LLMs

| Provider | Models |
|----------|--------|
| **OpenAI** | GPT-4o and later |
| **Anthropic** | Claude 3.5 Sonnet and later (including Claude 4 models) |
| **Vertex AI Gemini** | Gemini 1.5 Pro and later |

## Key Benefits

1. **Type Safety** — Deserialize directly to Java types with `.entity(YourType.class)`
2. **Guaranteed Compliance** — No more parsing failures or malformed JSON
3. **Performance** — Models can optimize internally for structured output
4. **Simplicity** — Just one line to enable: `AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT`
5. **Backward Compatibility** — Falls back to prompt-based `BeanOutputConverter` if not enabled

## How It Works

### 1. Define Your Data Model

```java
public record Book(
    String title,
    String author,
    String publisher,
    int yearPublished,
    List<String> topics
) {}

// Wrapper record for list responses (OpenAI doesn't support top-level arrays)
public record BookList(List<Book> books) {}
```

### 2. Enable Native Structured Output

```java
@RestController
public class BookController {

    private final ChatClient chatClient;

    public BookController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .build();
    }
}
```

### 3. Call the Model and Get Typed Results

```java
@GetMapping("/books/{topic}")
public BookList getBooks(@PathVariable String topic) {
    return chatClient.prompt()
            .user(u -> {
                u.text("Recommend 5 popular books for the topic: {topic}");
                u.param("topic", topic);
            })
            .call()
            .entity(new ParameterizedTypeReference<BookList>() {});
}
```

## Running the Demo

### Prerequisites

- Java 21+
- Maven
- Anthropic API key

### Setup

1. Set your API key as an environment variable:
   ```bash
   export ANTHROPIC_API_KEY=your-api-key-here
   ```

2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Test the endpoint:
   ```bash
   curl http://localhost:8080/books/java
   ```

## Configuration

```yaml
spring:
  ai:
    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
      chat:
        options:
          model: claude-opus-4-5
```

## Finding Available Chat Models

Not sure which models are available? Here are two ways to find out:

### 1. Check the Documentation

The [Anthropic Chat Documentation](https://docs.spring.io/spring-ai/reference/api/chat/anthropic-chat.html) lists all supported models and their capabilities.

### 2. Dig Through the Source Code

The `ChatClient` is the public API, but the underlying implementation reveals what models are available:

```
ChatClient → AnthropicChatModel → AnthropicApi.java
```

In `AnthropicApi.java`, you'll find the `ChatModel` enum with all available options:

```java
public enum ChatModel implements ChatModelDescription {
    CLAUDE_SONNET_4_5("claude-sonnet-4-5"),
    CLAUDE_OPUS_4_5("claude-opus-4-5"),
    CLAUDE_HAIKU_4_5("claude-haiku-4-5"),
    CLAUDE_OPUS_4_1("claude-opus-4-1"),
    CLAUDE_OPUS_4_0("claude-opus-4-0"),
    CLAUDE_SONNET_4_0("claude-sonnet-4-0"),
    CLAUDE_3_7_SONNET("claude-3-7-sonnet-latest"),
    CLAUDE_3_5_SONNET("claude-3-5-sonnet-latest"),
    CLAUDE_3_OPUS("claude-3-opus-latest"),
    CLAUDE_3_SONNET("claude-3-sonnet-20240229"),
    CLAUDE_3_5_HAIKU("claude-3-5-haiku-latest"),
    CLAUDE_3_HAIKU("claude-3-haiku-20240307");
}
```

Use the string value (e.g., `claude-opus-4-5`) in your configuration:

```yaml
spring:
  ai:
    anthropic:
      chat:
        options:
          model: claude-sonnet-4-5  # or any model from the enum
```

## Troubleshooting

### Model Doesn't Support Native Structured Output

Not all models support native structured output. If you use an unsupported model (like `claude-3-haiku-20240307`), you'll see an error like this:

```
org.springframework.ai.retry.NonTransientAiException: HTTP 400 -
{
  "type": "error",
  "error": {
    "type": "invalid_request_error",
    "message": "'claude-3-haiku-20240307' does not support output format."
  }
}
```

**Solution:** Switch to a supported model. For Anthropic, use Claude 3.5 Sonnet or later:

```yaml
spring:
  ai:
    anthropic:
      chat:
        options:
          model: claude-3-5-sonnet-latest  # or claude-sonnet-4-5, claude-opus-4-5
```

## How BeanOutputConverter Works (Non-Native Approach)

When native structured output is **not enabled**, Spring AI falls back to the `BeanOutputConverter` approach. 
Understanding this helps appreciate what native structured output improves upon.

### The Schema-in-Prompt Pattern

`BeanOutputConverter` generates a JSON schema from your Java type and appends format instructions directly to your user message:

```
Your response should be in JSON format.
Do not include any explanations, only provide a RFC8259 compliant JSON response
following this format without deviation.
Do not include markdown code blocks in your response.
Remove the ```json markdown from the output.
Here is the JSON Schema instance your output must adhere to:
```{"type":"object","properties":{"books":{"type":"array","items":{"type":"object","properties":{"title":{"type":"string"},"author":{"type":"string"},"publisher":{"type":"string"},"yearPublished":{"type":"integer"},"topics":{"type":"array","items":{"type":"string"}}},"required":["title","author","publisher","yearPublished","topics"]}}},"required":["books"]}```
```

### Key Classes in Spring AI

| Class | Purpose |
|-------|---------|
| `BeanOutputConverter<T>` | Generates JSON schema via `getFormat()`, parses response via `convert()` |
| `JsonSchemaGenerator` | Creates DRAFT_2020_12 JSON schemas using victools library |
| `DefaultChatClient` | Appends format instructions to user message before LLM call |

## Previous Approach (Before 1.1)

In versions prior to Spring AI 1.1, structured output relied on converters (`BeanOutputConverter`, `MapOutputConverter`, `ListOutputConverter`) that used prompt-based formatting and string parsing. The new native approach provides guaranteed schema compliance directly from the model.

## Resources

- [Spring AI Structured Output Documentation](https://docs.spring.io/spring-ai/reference/api/structured-output-converter.html#_native_structured_output)
- [Spring AI 1.1 Release Notes](https://spring.io/blog/2024/12/03/spring-ai-1-1-0-released)
