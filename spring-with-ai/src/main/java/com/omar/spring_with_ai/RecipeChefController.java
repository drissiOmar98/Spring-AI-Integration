package com.omar.spring_with_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller that interacts with an AI model to simulate
 * a "Chef Assistant" capable of suggesting seafood recipes.
 * <p>
 * This controller maintains a conversation history (messages exchanged
 * between the user and the AI) to provide contextual and consistent answers.
 * The context persists across multiple requests because messages are stored
 * in a list inside the controller.
 */
@RestController
@RequestMapping("/recipes/chef")
public class RecipeChefController {

    private final ChatClient chatClient;

    /**
     * Holds the history of the conversation: system, user, and assistant messages.
     * This makes the model maintain context between calls.
     */
    private final List<Message> conversation;


    /**
     * Initializes the AI chat client and sets up the system instruction
     * that guides the model's behavior permanently.
     * <p>
     * System prompt:
     *  - AI should only suggest seafood recipes.
     *  - If asked about anything else, it should respond with "I don't know."
     */
    public RecipeChefController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        this.conversation = new ArrayList<>();
        final String systemMessageString = """
        Suggest sea food recipe.
        If someone asks about something else, just say I don't know.
        """;
        // Add system message as the first message in the conversation history.
        final SystemMessage systemMessage = new SystemMessage(systemMessageString);
        this.conversation.add(systemMessage);

    }

    /**
     * Endpoint: GET /recipes/chef/suggest-recipe
     * <p>
     * Accepts a user message and sends it to the AI, along with the entire
     * conversation history, so the model can reply with context awareness.
     *
     * @param message The user prompt. Defaults to "Suggest a recipe for dinner."
     * @return The AI-generated response.
     */
    @GetMapping("/suggest-recipe")
    public String suggestRecipe(
            @RequestParam(
                    name = "message",
                    defaultValue = "Suggest a recipe for dinner"
            ) String message
    ) {
        // Add the new user message to the conversation history.
        final Message userMessage = new UserMessage(message);
        this.conversation.add(userMessage);

        // Send the full conversation to the model to generate a contextual reply.
        String modelResponse = this.chatClient.prompt()
                .messages(this.conversation)
                .call()
                .content();

        // Add the assistant's response back into the conversation for continuity.
        final Message assistantMessage = new AssistantMessage(modelResponse);
        this.conversation.add(assistantMessage);
        return modelResponse;
    }
}