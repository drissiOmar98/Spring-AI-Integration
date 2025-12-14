package com.omar.tool_calling.employee;

import com.omar.tool_calling.datetime.DateTimeTools;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 👥 Employee Chat Controller - AI-Powered HR Assistant for Drissi Company
 * <p>
 * REST controller providing AI-powered HR assistance for Drissi Company employees and managers.
 * Integrates employee management tools with date/time capabilities for comprehensive
 * HR queries and natural language interaction.
 * <p>
 * Architecture: Natural Language → AI Analysis → Tool Calling → Structured Response
 * <p>
 * Specialized Features:
 * - Drissi Company-specific HR knowledge base
 * - Employee data access via EmployeeTools
 * - Date/time calculations via DateTimeTools
 * - Comprehensive logging for audit trails
 * - Input validation and structured responses
 * <p>
 * Use Cases at Drissi Company:
 * - Employee information lookup
 * - Leave management and planning
 * - Team availability checking
 * - HR policy queries
 * - Date-sensitive HR operations
 */
@RestController
@RequestMapping("/api")
class EmployeeController {
    private final ChatClient chatClient;

    /**
     * Constructs AI-powered HR assistant specialized for SivaLabs
     *
     * @param builder ChatClient builder for creating AI chat instances
     * @param employeeTools HR-specific tools for employee operations
     *
     * @apiNote Configuration includes:
     *          - Company-specific system prompt
     *          - HR tools (EmployeeTools) for employee operations
     *          - DateTimeTools for date calculations
     *          - Logging advisor for monitoring
     */
    EmployeeController(ChatClient.Builder builder, EmployeeTools employeeTools) {
        this.chatClient = builder
                .defaultSystem("""
                You are a helpful assistant for Drissi company.
                You always respond based on the data you have from tools available to you.
                If you don't know the answer, you will respond with "I don't know".
                """)
                .defaultTools(
                        employeeTools,        // HR operations (employee lookup, leave management)
                        new DateTimeTools()   // Date/time calculations
                )
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }


    @PostMapping("/hr/chat")  // Specific HR chat endpoint
    Output chat(@RequestBody @Valid Input input) {
        String response = chatClient
                .prompt(input.prompt()).call().content();
        return new Output(response);
    }

    /**
     * 📥 Input DTO - HR Chat Request
     *
     * @param prompt The user's HR-related query (validated to be non-blank)
     *               Examples:
     *               - "Show me details for employee E001"
     *               - "Who's on vacation next month?"
     *               - "Apply sick leave for me tomorrow"
     */
    record Input(@NotBlank String prompt) {}

    /**
     * 📤 Output DTO - HR Chat Response
     *
     * @param content AI-generated response to the HR query
     *                Contains natural language answer enhanced with tool data
     */
    record Output(String content) {}
}