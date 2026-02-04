package com.omar.spring_ai_web_search;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest.WebSearchOptions;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest.WebSearchOptions.SearchContextSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSearchController {

    private final ChatClient chatClient;

    /**
     * 🏗️ Constructs and configures the ChatClient
     * for web search capabilities.
     *
     * <p>
     * This constructor:
     * </p>
     * <ol>
     *   <li>Loads the OpenAI API key</li>
     *   <li>Configures search-enabled chat options</li>
     *   <li>Builds the OpenAI chat model</li>
     *   <li>Initializes the Spring AI ChatClient</li>
     * </ol>
     */
    public WebSearchController() {
        // 🔐 Retrieve OpenAI API key from environment variables
        // This prevents exposing sensitive credentials in source code
        String apiKey = System.getenv("OPENAI_API_KEY");

        // ⚙️ Configure chat options with a search-enabled model
        // "gpt-4o-search-preview" allows real-time web access
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("gpt-4o-search-preview")
                .build();

        // 🤖 Build the OpenAI chat model with API credentials
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                // Configure OpenAI API with authentication
                .openAiApi(OpenAiApi.builder().apiKey(apiKey).build())
                // Apply default chat options
                .defaultOptions(chatOptions)
                .build();

        // 🚀 Create the ChatClient using the configured model
        // This client will be used to send prompts with web search enabled
        this.chatClient = ChatClient.builder(openAiChatModel).build();
    }


    /**
     * 🤖 GPT-5 Web Search Demo
     * <p>
     * Demonstrates how to use Spring AI's WebSearchOptions with a search-enabled GPT-5 model to retrieve
     * up-to-date information from the web.
     *
     * <p>
     * This endpoint sends a prompt to the AI model with web search enabled and returns a real-time response,
     * avoiding outdated training data limitations.
     * </p>
     *
     * <p>
     * 🚀 Key Features:
     * </p>
     * <ul>
     *   <li>✅ Enables live web search for the request</li>
     *   <li>✅ Uses LOW search context for faster responses</li>
     *   <li>✅ Demonstrates per-request AI configuration</li>
     * </ul>
     *
     * <p>
     * ⚙️ You can adjust {@link SearchContextSize} to:
     * LOW / MEDIUM / HIGH depending on accuracy needs.
     * </p>
     *
     * @return AI-generated response using real-time web data
     */
    @GetMapping("/gpt5")
    public String gpt5() {

        // 🌐 Configure web search behavior for this request
        // LOW = smaller context, faster response
        WebSearchOptions webSearchOptions = new WebSearchOptions(
                SearchContextSize.LOW,
                null // No location filter applied
        );

        // ⚙️ Build OpenAI chat options with web search enabled
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                // Attach web search configuration
                .webSearchOptions(webSearchOptions)
                .build();

        // 🚀 Send prompt to the AI model and retrieve response
        return chatClient.prompt()

                // 👤 User prompt requesting real-time information
                .user("Tell me an interesting fact about GPT-5")
                // Apply custom options for this request
                .options(options)
                .call()
                .content();
    }


}