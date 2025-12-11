package com.omar.tool_calling.action;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 📋 Task Management Tools - Spring AI Tool Calling for Task Operations
 * <p>
 * This component exposes a set of task-related operations as callable AI tools
 * <p>
 * These tools allow an AI model to:
 * - Create tasks
 * - Update their status
 * - Assign or reassign tasks
 * <p>
 * Spring AI automatically:
 * 1. Detects when the model wants to call a tool
 * 2. Extracts parameters from natural language
 * 3. Executes the matching method
 * 4. Returns the result back to the model for a richer response
 * <p>
 * This simulates a lightweight project-management backend.
 * In real scenarios, the operations would integrate with a database,
 * notification system, or workflow engine.
 */
@Component
public class TaskManagementTools {


    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong taskIdGenerator = new AtomicLong(1);

    private record Task(Long id, String title, String description, String assignee, TaskStatus status) {}

    /**
     * 🆕 Tool: Create a new task
     * <p>
     * Creates a task with a title, description, and assignee.
     * Default status is {@code PENDING}.
     * <p>
     * This tool will be invoked when the AI receives user requests like:
     * "Create a task for reviewing the code and assign it to Sarah."
     *
     * @param title       Task title
     * @param description Task description
     * @param assignee    Person responsible for the task
     * @return structured {@link TaskResult}
     */
    @Tool(description = "Create a new task with title, description, and assignee")
    public TaskResult createTask(String title, String description, String assignee) {
        Long taskId = taskIdGenerator.getAndIncrement();
        Task task = new Task(taskId, title, description, assignee, TaskStatus.PENDING);
        tasks.put(taskId, task);

        // In real implementation: save to database, send notification email
        return new TaskResult(taskId, title, "PENDING", assignee,
                "Task created successfully and assigned to " + assignee);
    }

    /**
     * 🔄 Update Task Status Tool
     * <p>
     * Updates the status of an existing task. The AI understands status transitions
     * and can call this tool when users mention task progress or completion.
     *
     * @param taskId Numeric ID of the task to update (extracted from context)
     * @param status New status for the task (AI understands enum values from natural language)
     * @return TaskResult with updated status information
     *
     * @example AI Interaction:
     * User: "Mark task 5 as completed"
     * AI → Calls: updateStatus(5L, TaskStatus.COMPLETED)
     * Returns: Task 5 status updated to COMPLETED
     *
     * @apiNote Status Transition Rules:
     *          - PENDING → IN_PROGRESS (task started)
     *          - IN_PROGRESS → COMPLETED (task finished)
     *          - Any → CANCELLED (task cancelled)
     *          - COMPLETED → IN_PROGRESS (reopened task)
     */
    @Tool(description = "Update task status by task ID")
    public TaskResult updateStatus(Long taskId, TaskStatus status) {
        Task existingTask = tasks.get(taskId);
        if (existingTask == null) {
            return new TaskResult(taskId, "", "ERROR", "", "Task not found");
        }

        Task updatedTask = new Task(existingTask.id(), existingTask.title(),
                existingTask.description(), existingTask.assignee(), status);
        tasks.put(taskId, updatedTask);

        // In real implementation: update database, trigger workflow, send notifications
        return new TaskResult(taskId, updatedTask.title(), status.toString(),
                updatedTask.assignee(), "Task status updated to " + status);
    }


    /**
     * 📌 Tool: Assign or reassign a task
     * <p>
     * Enables the AI to change the assignee of a task.
     * Example natural-language triggers:
     * - "Assign task 3 to Ahmed."
     * - "Reassign the bug fix task to Mary."
     *
     * @param taskId      ID of the task to reassign
     * @param newAssignee Name of the new assignee
     * @return updated {@link TaskResult} or error details
     */
    @Tool(description = "Assign or reassign a task to a different person")
    public TaskResult assignTask(Long taskId, String newAssignee) {
        Task existingTask = tasks.get(taskId);
        if (existingTask == null) {
            return new TaskResult(taskId, "", "ERROR", "", "Task not found");
        }

        Task updatedTask = new Task(existingTask.id(), existingTask.title(),
                existingTask.description(), newAssignee, existingTask.status());
        tasks.put(taskId, updatedTask);

        // In real implementation: update database, send notification to new assignee
        return new TaskResult(taskId, updatedTask.title(), updatedTask.status().toString(),
                newAssignee, "Task reassigned to " + newAssignee);
    }
}