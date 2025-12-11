package com.omar.tool_calling.action;

/**
 * 📊 Task Status Enumeration
 * <p>
 * Defines the valid lifecycle states for tasks in the system.
 * AI models automatically understand these enum values when calling tools.
 */
public enum TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED
}
