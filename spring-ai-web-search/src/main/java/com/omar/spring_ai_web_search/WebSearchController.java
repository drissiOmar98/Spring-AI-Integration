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



}