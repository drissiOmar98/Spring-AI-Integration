package com.omar.spring_with_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that provides AI-powered seafood recipe suggestions.
 * <p>
 * This controller uses Spring AI's {@link ChatClient} to interact with an LLM.
 * It enforces a system instruction so the model only suggests seafood recipes.
 * If the user asks for something unrelated, the model responds with
 * “I don't know”.
 * </p>
 */
@RestController
@RequestMapping("/recipes/sea-food")
public class SeaFoodRecipeController {

    private final ChatClient chatClient;

    public SeaFoodRecipeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Endpoint that asks the AI model to suggest a seafood recipe.
     * The model is constrained using a system message to ONLY answer
     * seafood-related questions.
     *
     * @param message Optional user message. Defaults to a generic request.
     * @return AI-generated seafood recipe OR "I don't know" if off-topic.
     */
    @GetMapping("/suggest-recipe")
    public String suggestRecipe(
            @RequestParam(
                    name = "message",
                    defaultValue = "Suggest a recipe for dinner"
            ) String message
    ) {
        // System-level instruction to influence model behavior.
        final String systemMessage = """
        Suggest sea food recipe.
        If someone asks about something else, just say I don't know.
        """;
        return this.chatClient.prompt()
                .system(c -> c.text(systemMessage))
                .user(message)
                .call()
                .content();
    }

}