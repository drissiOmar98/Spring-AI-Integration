package com.omar.spring_io_mcp_server;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringIoMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIoMcpServerApplication.class, args);
	}


	@Bean
	public List<ToolCallback> springIOSessionTools(SessionTools sessionTools) {
		return List.of(ToolCallbacks.from(sessionTools));
	}

}
