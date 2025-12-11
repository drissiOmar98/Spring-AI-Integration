package com.omar.tool_calling.action;


/**
 * 🏷️ Task Result DTO - Structured Response for Tool Operations
 * <p>
 * Java record representing the structured output from task management operations.
 * Used by AI models to understand and communicate tool execution results.
 *
 * @param taskId Unique identifier for the task
 * @param title Title of the task
 * @param status Current status of the task (PENDING, IN_PROGRESS, etc.)
 * @param assignee Person responsible for the task
 * @param message Human-readable operation result message
 */
public record TaskResult(Long taskId,
                         String title,
                         String status,
                         String assignee,
                         String message
) {
}
