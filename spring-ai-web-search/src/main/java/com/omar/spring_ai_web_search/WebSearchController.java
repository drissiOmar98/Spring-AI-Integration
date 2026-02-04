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

/**
 * 🌐 WebSearchController
 * <p>
 * REST controller demonstrating real-time web search capabilities using Spring AI and OpenAI search-enabled models.
 * <p>
 * This controller provides multiple endpoints to showcase how to integrate Spring AI's ChatClient with web search:
 * </p>
 * <ul>
 *   <li>🤖 <b>/gpt5</b> - Demonstrates a GPT-5 prompt with real-time web search</li>
 *   <li>📰 <b>/news</b> - Curates recent Spring Framework news with structured summaries</li>
 *   <li>🍽️ <b>/restaurants</b> - Recommends exactly 3 restaurants based on cuisine, price, and location</li>
 * </ul>
 *
 * <p>
 * 🚀 Key Features:
 * </p>
 *
 * <ul>
 *   <li>✅ Uses Spring AI ChatClient to communicate with OpenAI models</li>
 *   <li>✅ WebSearchOptions allow for context size tuning and user location awareness</li>
 *   <li>✅ Custom system and user prompts enable structured, reliable responses</li>
 *   <li>✅ Supports real-time data retrieval to avoid "As of my last update..." limitations</li>
 *   <li>✅ Demonstrates location-based queries for personalized results</li>
 * </ul>
 * <p>
 * 📖 Usage:
 * </p>
 *
 * <ul>
 *   <li>Call <b>/gpt5</b> to get interesting GPT-5 facts with live web search</li>
 *   <li>Call <b>/news</b> to get curated Spring ecosystem news summaries</li>
 *   <li>Call <b>/restaurants</b> to get location-aware restaurant recommendations</li>
 * </ul>
 */
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


    /**
     * 📰 Spring Ecosystem News Aggregator
     * <p>
     * Retrieves the latest Spring Framework ecosystem news using real-time web search and AI-powered analysis.
     * <p>
     * This endpoint queries authoritative sources and generates a structured, developer-friendly summary
     * including impact level and action items.
     * </p>
     * <p>
     * 🚀 Features:
     * </p>
     * <ul>
     *   <li>✅ Uses live web search for up-to-date information</li>
     *   <li>✅ Filters trusted Spring sources</li>
     *   <li>✅ Produces scannable technical summaries</li>
     *   <li>✅ Helps developers stay informed efficiently</li>
     * </ul>
     *
     * <p>
     * ⚙️ Search Scope:
     * </p>
     * <p>
     * Defaults to the past 7 days and automatically expands to the last month when recent data is limited.
     * </p>
     *
     * @return Curated summary of recent Spring ecosystem news
     */
    @GetMapping("/news")
    public String currentNews() {

        // 🌐 Configure web search settings for this request
        // LOW context = faster response with focused results
        WebSearchOptions webSearchOptions = new WebSearchOptions(
                WebSearchOptions.SearchContextSize.LOW,
                null // No geographic filtering applied
        );

        // ⚙️ Build OpenAI options with web search enabled
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .webSearchOptions(webSearchOptions)
                .build();

        // 🧠 System prompt: defines the AI's role and behavior
        String systemPrompt = """
            You are a Spring Framework expert and technical news analyst. Your role is to:
            
            1. Search for and identify the most recent and significant developments in the Spring ecosystem
            2. Focus on official announcements, major releases, security updates, and breaking changes
            3. Prioritize information from authoritative sources like:
               - Official Spring blog (spring.io/blog)
               - GitHub releases and changelogs
               - Spring team announcements
               - Major tech publications covering Spring
            
            4. Structure your response with:
               - **Date and Source** for each news item
               - **Impact Level** (Critical/Major/Minor)
               - **Brief Summary** in 2-3 sentences
               - **Action Items** for developers if applicable
            
            5. Filter out generic Java news unless directly Spring-related
            6. If no significant Spring news is found in the last week, expand to the last month
            7. Always verify information currency - ignore outdated articles
            
            Present the information in a clear, scannable format that busy developers can quickly digest.
            """;

        // 👤 User prompt: defines the specific search request
        String userPrompt = """
            Search for the latest Spring Framework ecosystem news from the past 7 days, including:
            
            - Spring Boot releases and updates
            - Spring Security announcements  
            - Spring Cloud developments
            - New Spring projects or major updates
            - Breaking changes or deprecations
            - Security vulnerabilities and patches
            - Spring Tools and IDE integration updates
            - Performance improvements or new features
            - Community events or important blog posts
            
            Focus on news that would impact Spring developers in their daily work.
            If limited recent news, include significant developments from the past month.
            """;

        // 🚀 Send prompt to AI model and retrieve analyzed news summary
        return chatClient.prompt()
                // Apply system-level instructions
                .system(systemPrompt)
                // Apply user search request
                .user(userPrompt)
                // Enable web search options
                .options(options)
                .call()
                .content();
    }


    /**
     * 🍽️ Local Restaurant Finder
     * <p>
     * This endpoint provides exactly 3 restaurant recommendations based on user-defined criteria like cuisine, price range, and location.
     * It leverages Spring AI's ChatClient with OpenAI's search-enabled models to access real-time information from the web.
     * <p>
     * 🚀 Features:
     * </p>
     * <ul>
     *   <li>✅ Real-time web search with user location awareness</li>
     *   <li>✅ Configurable search context size for performance vs. accuracy</li>
     *   <li>✅ Strict enforcement of exactly 3 restaurant recommendations</li>
     *   <li>✅ Custom system and user prompts to ensure structured responses</li>
     * </ul>
     *
     * <p>
     * ⚙️ Default Location & Parameters:
     * </p>
     * <ul>
     *   <li>City: Cleveland</li>
     *   <li>Region: Ohio</li>
     *   <li>Country: US</li>
     *   <li>Timezone: America/New_York</li>
     *   <li>Cuisine: any</li>
     *   <li>Price Range: $ (budget-friendly)</li>
     * </ul>
     *
     * @param cuisine   Type of cuisine to filter (default: any)
     * @param priceRange Price category ($, $$, $$$, $$$$)
     * @param city      User's city (default: Cleveland)
     * @param region    User's region/state (default: Ohio)
     * @param country   User's country (default: US)
     * @param timezone  User's timezone (default: America/New_York)
     * @return AI-generated response with exactly 3 recommended restaurants
     */
    @GetMapping("/restaurants")
    public String findRestaurants(@RequestParam(defaultValue = "any") String cuisine,
                                  @RequestParam(defaultValue = "$") String priceRange,
                                  @RequestParam(defaultValue = "Cleveland") String city,
                                  @RequestParam(defaultValue = "Ohio") String region,
                                  @RequestParam(defaultValue = "US") String country,
                                  @RequestParam(defaultValue = "America/New_York") String timezone) {

        // 🌐 Define user's location for web search
        WebSearchOptions.UserLocation userLocation = new WebSearchOptions.UserLocation(
                "approximate",
                new WebSearchOptions.UserLocation.Approximate(city, country, region, timezone)
        );

        // ⚙️ Configure WebSearchOptions with MEDIUM context size for balanced performance
        WebSearchOptions webSearchOptions = new WebSearchOptions(
                WebSearchOptions.SearchContextSize.MEDIUM, // Reduced from HIGH for faster response
                userLocation
        );

        // ⚡ Build OpenAI chat options with web search
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .webSearchOptions(webSearchOptions)
                .build();

        // 🧠 System prompt: AI behaves as a local restaurant expert
        String systemPrompt = String.format("""
        You are a local restaurant expert. You MUST provide exactly 3 restaurant recommendations - no more, no less.
        
        **CRITICAL REQUIREMENTS:**
        - Return exactly 3 restaurants (never 1, 2, or more than 3)
        - All restaurants must be currently OPEN and accepting customers
        - All must match: %s cuisine and %s price range
        - All must have recent positive reviews
        
        **Response Format for each of the 3 restaurants:**
        **Restaurant Name** - Neighborhood
        **Specialty** - What they're famous for
        **Price & Rating** - Price range and rating
        **Status & Contact** - Current hours and reservation info
        
        If you cannot find 3 restaurants that meet the exact criteria, expand your search to include:
        1. Nearby neighborhoods
        2. Slightly broader cuisine categories
        3. Adjacent price ranges
        
        But you MUST return exactly 3 recommendations.
        """,
                cuisine.equals("any") ? "any" : cuisine,
                getPriceRangeDescription(priceRange));

        // 👤 User prompt: specific query for AI to execute
        String userPrompt = String.format("""
        I need exactly 3 %s restaurant recommendations near me in the %s price range.
        
        **Requirements:**
        - All 3 must be currently OPEN (not closed)
        - All 3 must be accepting customers today
        - All 3 should have good recent reviews
        - Give me practical info: address, hours, phone, reservation needs
        
        **Important:** I need exactly 3 options - please don't give me fewer than 3 restaurants.
        """,
                cuisine.equals("any") ? "great" : cuisine,
                getPriceRangeDescription(priceRange));

        // 🚀 Send prompts to AI model and retrieve response
        return chatClient.prompt()
                .system(systemPrompt) // System instructions
                .user(userPrompt)     // User request
                .options(options)     // Web search options
                .call()               // Execute request
                .content();           // Extract text content
    }

    /**
     * 🏷️ Converts price range symbols to descriptive labels.
     *
     * @param priceRange $-$$$$ price symbols
     * @return descriptive string for prompts (e.g., "budget-friendly")
     */
    private String getPriceRangeDescription(String priceRange) {
        return switch (priceRange) {
            case "$" -> "budget-friendly";
            case "$$" -> "moderate";
            case "$$$" -> "upscale";
            case "$$$$" -> "fine dining";
            default -> "moderate";
        };
    }





}