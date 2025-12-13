package com.omar.spring_ai_04_chat_with_ollama;

import com.omar.spring_ai_04_chat_with_ollama.functions.config.WeatherConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class SpringAi04ChatWithOllamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAi04ChatWithOllamaApplication.class, args);
	}

}
