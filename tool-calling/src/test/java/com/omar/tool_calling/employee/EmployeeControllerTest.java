package com.omar.tool_calling.employee;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @ParameterizedTest
    @ValueSource(strings = {
            "What is today's date?",
            "Get details of employee id emp1002 of Drissi company",
            "Which Drissi company employees are on leave on 2025-01-01?",
            "Which Drissi company employees are on leave on today?",
            "Apply leave for Drissi company employee id emp1001 on 2025-04-01",
    })
    void chat(String prompt) {
        MvcTestResult testResult = mockMvcTester.post().uri("/api/hr/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "prompt": "%s"
                        }
                        """.formatted(prompt))
                .exchange();
        assertThat(testResult)
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(EmployeeController.Output.class).satisfies(output -> {
                    assertThat(output.content()).isNotBlank();
                    System.out.println("Response:\n"+output.content());
                });

    }

}