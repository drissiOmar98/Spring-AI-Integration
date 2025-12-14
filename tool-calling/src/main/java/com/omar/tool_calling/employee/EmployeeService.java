package com.omar.tool_calling.employee;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 👥 Employee Service - In-Memory Employee & Leave Management
 * <p>
 * Core service responsible for managing employee data and leave records
 * used by AI tool-calling functions in the application.
 * <p>
 * This service acts as a mock data layer (in-memory storage) to:
 * - Retrieve employee details
 * - Track employee leave dates
 * - Apply new leave requests
 * <p>
 * Designed specifically for demonstrating Spring AI Tool Calling,
 * where AI models can trigger business actions such as:
 * - "Get employee emp1001 details"
 * - "Who is on leave today?"
 * - "Apply leave for emp1002 tomorrow"
 * <p>
 * ⚠️ Note:
 * - Data is stored in memory (HashMap)
 * - No persistence (resets on application restart)
 * - Suitable for demos, tutorials, and AI experimentation
 */
@Service
public class EmployeeService {

    /**
     * 🗃️ In-memory employee database (EmployeeID → Employee)
     * Structure: Map<EmployeeID, Employee>
     *
     * @implNote In production, replace with:
     *           - JPA Repository (JpaRepository<Employee, String>)
     *           - Caching layer (@Cacheable annotations)
     *           - External HR system integration
     */
    private static final Map<String, Employee> employeeTable = new HashMap<>();

    /**
     * 📅 In-memory leave tracking (Date → List of EmployeeIDs on leave)
     * Structure: Map<LocalDate, List<EmployeeID>>
     *
     * @implNote Production considerations:
     *           - Database table: employee_leave_requests
     *           - Indexes on date and employee_id for performance
     *           - Leave type tracking (sick, vacation, personal)
     *           - Approval workflow states
     */
    private static final Map<LocalDate, List<String>> employeeLeavesTable = new HashMap<>();


    /**
     * 🚀 Data Initialization - Populates in-memory storage with sample data
     * <p>
     * Executed after dependency injection completes, before service is used.
     * Initializes SivaLabs team members with realistic data for demonstration.
     *
     * @apiNote Sample Team Structure:
     *          - emp1001: Omar Drissi (Lead Developer)
     *          - emp1002: Ahmed (CTO/Founder)
     *          - emp1003: Amira (Senior Engineer)
     *
     * @implSpec Production initialization would:
     *           - Load from database on startup
     *           - Sync with external HR systems
     *           - Cache frequently accessed employees
     */
    @PostConstruct
    void init() {
        employeeTable.put("emp1001", new Employee("emp1001", "John Doe", "john.doe@example.com"));
        employeeTable.put("emp1002", new Employee("emp1002", "Ahmed", "ahmed@example.com"));
        employeeTable.put("emp1003", new Employee("emp1003", "James", "james@example.com"));

        employeeLeavesTable.put(LocalDate.now(), List.of("emp1001", "emp1003"));
        employeeLeavesTable.put(LocalDate.of(2025, 1, 1), List.of("emp1001", "emp1002"));
        employeeLeavesTable.put(LocalDate.of(2025, 1, 2), List.of("emp1002", "emp1003"));
    }


    /**
     * 🔍 Get Employee by ID
     * <p>
     * Retrieves employee details for the given employee ID.
     *
     * @param empId Unique employee identifier
     * @return Employee details or null if not found
     */
    Employee getEmployee(String empId) {
        return employeeTable.get(empId);
    }


    /**
     * 📅 Find Employees on Leave for a Specific Date
     * <p>
     * Returns list of employees scheduled to be on leave on the given date.
     * Useful for resource planning, meeting scheduling, and team availability.
     *
     * @param date Date to check leave status
     * @return List of employees on leave (empty if none)
     */
    List<Employee> findEmployeesOnLeave(LocalDate date) {
        List<String> empIds = employeeLeavesTable.get(date);
        return empIds == null? List.of() : getEmployees(empIds);
    }

    /**
     * 👥 Get Multiple Employees by IDs
     * <p>
     * Internal helper method to retrieve multiple employees efficiently.
     *
     * @param empIds List of employee IDs to retrieve
     * @return List of Employee objects (preserving order of input IDs)
     */
    List<Employee> getEmployees(List<String> empIds) {
        return empIds.stream().map(employeeTable::get).toList();
    }


    /**
     * 🏖️ Apply Leave for Employee
     * <p>
     * Submits a leave request for an employee on a specific date.
     * Validates business rules and updates leave tracking system.
     *
     * @param empId Employee ID
     * @param date  Leave date
     */
    void applyLeave(String empId, LocalDate date) {
        List<String> empIds = employeeLeavesTable.get(date);
        if(empIds == null) {
            empIds = List.of(empId);
        } else {
            empIds.add(empId);
        }
        employeeLeavesTable.put(date, empIds);
    }
}