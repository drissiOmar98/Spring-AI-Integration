package com.omar.tool_calling.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 👨‍💼 Employee Tools - AI Tool Calling for Employee & Leave Management
 * <p>
 * Spring AI tool component that exposes employee-related operations
 * as AI-callable functions. This class allows an AI model to retrieve
 * employee details, check leave schedules, and apply leave requests
 * using natural language.
 * <p>
 * Registered as a Spring {@link Service}, each method annotated with
 * {@link Tool} becomes automatically discoverable and invokable
 * by the AI during a conversation.
 * <p>
 * Typical Flow:
 * User → Natural Language Prompt →
 * AI Detects Required Action →
 * Tool Method Execution →
 * AI Responds with Structured Result
 * <p>
 * Example AI Interactions:
 * <ul>
 *   <li>"Show me details of employee EMP-101"</li>
 *   <li>"Who is on leave on 2025-01-11?"</li>
 *   <li>"Apply leave for Omar on 2025-12-31"</li>
 * </ul>
 */
@Service
public class EmployeeTools {

    private static final Logger log = LoggerFactory.getLogger(EmployeeTools.class);

    private final EmployeeService employeeService;

    public EmployeeTools(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    /**
     * 🔍 Retrieve employee details by employee ID.
     * <p>
     * This tool is automatically invoked by the AI when a user asks
     * about a specific employee.
     * <p>
     * Example:
     * User: "Get employee details for Omar"
     * AI: Calls {@code getEmployee("EMP-101")}
     *
     * @param empId Unique employee identifier (e.g. EMP-101)
     * @return Employee details including name, role, and status
     */
    @Tool(description = "Get employee details for a given employee id of Drissi company")
    public Employee getEmployee(String empId) {
        log.info("Getting employee: {}", empId);
        Employee employee = employeeService.getEmployee(empId);
        log.info("Employee: {}", employee);
        return employee;
    }

    /**
     * 📅 Find employees who are on leave for a specific date.
     * <p>
     * Enables the AI to answer questions related to availability
     * and leave planning.
     * <p>
     * Example:
     * User: "Who is on leave on 2025-01-20?"
     * AI: Calls {@code findEmployeesOnLeave(LocalDate.of(2025, 1, 20))}
     *
     * @param date Leave date in YYYY-MM-DD format
     * @return List of employees on leave for the given date
     */
    @Tool(description = "Find employees of Drissi company who are on leave for a given date in YYYY-MM-DD format")
    public List<Employee> findEmployeesOnLeave(LocalDate date) {
        log.info("Finding employees on leave for date: {}", date);
        List<Employee> employeesOnLeave = employeeService.findEmployeesOnLeave(date);
        log.info("Employees on leave: {} on date {}", employeesOnLeave, date);
        return employeesOnLeave;
    }


    /**
     * ✍️ Apply leave for a specific employee on a given date.
     * <p>
     * Allows the AI to perform an action (state change) rather than
     * just information retrieval.
     * <p>
     * Example:
     * User: "Apply leave for Omar on 2025-02-01"
     * AI: Calls {@code applyLeave("EMP-101", LocalDate.of(2025, 2, 1))}
     *
     * @param empId Employee identifier
     * @param date  Leave date in YYYY-MM-DD format
     */
    @Tool(description = "Apply leave for a given employee id of Drissi company and date in YYYY-MM-DD format")
    public void applyLeave(String empId, LocalDate date) {
        log.info("Applying leave for employee: {} on date: {}", empId, date);
        employeeService.applyLeave(empId, date);
    }


}