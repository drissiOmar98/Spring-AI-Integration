package com.omar.tool_calling.action;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 🗂️ Task Management Controller — AI-Driven Task Operations via Tool Calling
 * <p>
 * This REST controller exposes an endpoint that allows AI models to manage tasks
 * using Spring AI’s tool calling mechanism. It integrates the {@link TaskManagementTools}
 * component so the AI can:
 * <ul>
 *     <li>Create tasks</li>
 *     <li>Update task status</li>
 *     <li>Assign or reassign tasks</li>
 * </ul>
 * The controller acts as a conversational gateway where natural language input
 * from the user is interpreted by the AI model, which then decides when to
 * invoke the appropriate task-related tools.
 * <p>
 * Workflow:
 * User message → ChatClient → AI Model → Tool Detection → Tool Execution →
 * Response returned with updated task information.
 * <p>
 * Benefits:
 * <ul>
 *     <li>Enables natural-language task management</li>
 *     <li>Demonstrates real-world function calling with structured data</li>
 *     <li>Simplifies backend operations by delegating logic to AI-driven tools</li>
 * </ul>
 */
@RestController
public class TaskManagementController {

    private final ChatClient chatClient;
    private final TaskManagementTools taskManagementTools;

    public TaskManagementController(ChatClient.Builder builder, TaskManagementTools taskManagementTools) {
        this.chatClient = builder.build();
        this.taskManagementTools = taskManagementTools;
    }

    /**
     * 🔹 Task Management Endpoint
     * <p>
     * Accepts a natural-language user message (e.g.:
     * "Create a task for validating invoices and assign it to Sarah")
     * and lets the AI model interpret the message and invoke the appropriate
     * tools from {@link TaskManagementTools}.
     * <p>
     * The AI:
     * <ol>
     *     <li>Analyzes the user request</li>
     *     <li>Determines which tool(s) to call</li>
     *     <li>Executes the tool(s) with extracted parameters</li>
     *     <li>Responds with structured results (TaskResult)</li>
     * </ol>
     *
     * Endpoint:
     * <b>GET /tasks?message=...</b>
     *
     * @param message The user's request expressed in natural language
     * @return The AI-generated response, including tool-executed task data
     */
    @GetMapping("/tasks")
    public String createTask(@RequestParam String message) {
        return chatClient.prompt()
                .tools(taskManagementTools)
                .user(message)
                .call()
                .content();
    }
}