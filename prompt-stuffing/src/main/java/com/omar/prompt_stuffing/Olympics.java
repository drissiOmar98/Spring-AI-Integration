package com.omar.prompt_stuffing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * REST controller demonstrating the <b>Prompt Stuffing</b> technique using Spring AI.
 *
 * <p>
 * Prompt stuffing is a simple "Bring Your Own Data" approach where we inject
 * additional context into the prompt sent to a Large Language Model (LLM).
 * </p>
 *
 * <p>
 * Instead of relying solely on the model’s training data, we provide
 * custom domain knowledge (in this case Olympic sports information)
 * that the model should use when generating its answer.
 * </p>
 *
 * <p>
 * This controller loads:
 * <ul>
 *     <li>A context document containing Olympic sports data</li>
 *     <li>A prompt template used to construct the final prompt</li>
 * </ul>
 * and sends them to the LLM using {@link ChatClient}.
 * </p>
 *
 * <p>
 * Endpoint Example:
 * <pre>
 * GET /olympics/2024
 * GET /olympics/2024?stuffit=true
 * GET /olympics/2024?message=What sports are new in 2024?
 * </pre>
 * </p>
 */
@RestController
@RequestMapping("/olympics")
public class Olympics {

    private static final Logger log = LoggerFactory.getLogger(Olympics.class);
    private final ChatClient chatClient;

    /**
     * Resource containing the domain data that can be injected into the prompt.
     *
     * <p>
     * This file contains Olympic sports information and will be
     * optionally inserted into the prompt when the <code>stuffit</code>
     * parameter is enabled.
     * </p>
     */
    @Value("classpath:/docs/olympic-sports.txt")
    private Resource docsToStuffResource;

    /**
     * Prompt template used to structure the final prompt sent to the LLM.
     *
     * <p>
     * The template contains placeholders such as:
     * <ul>
     *     <li>{question}</li>
     *     <li>{context}</li>
     * </ul>
     * which are dynamically replaced at runtime.
     * </p>
     */
    @Value("classpath:/prompts/olympic-sports.st")
    private Resource olympicSportsResource;

    public Olympics(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Endpoint demonstrating prompt stuffing for Olympic sports questions.
     *
     * <p>
     * If <b>stuffit=true</b>, the Olympic sports document will be injected
     * into the prompt as additional context.
     * </p>
     *
     * <p>
     * If <b>stuffit=false</b>, the LLM will answer using only its
     * training data without the additional context.
     * </p>
     *
     * @param message User question sent to the LLM
     * @param stuffit Flag indicating whether to inject the context document
     *                into the prompt (prompt stuffing)
     * @return AI-generated response from the LLM
     * @throws IOException if the context file cannot be read
     */
    @GetMapping("/2024")
    public String get2024OlympicSports(
            @RequestParam(value = "message", defaultValue = "What sports are being included in the 2024 Summer Olympics?") String message,
            @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit
    ) throws IOException {
        // Load Olympic sports context document
        String sports = docsToStuffResource.getContentAsString(Charset.defaultCharset());
        log.info("Sports: {}", sports);
        return chatClient.prompt()
                .user(u -> {

                    // Load prompt template
                    u.text(olympicSportsResource);

                    // Insert user question into the template
                    u.param("question",message);

                    // Insert context only if prompt stuffing is enabled
                    u.param("context", stuffit ? sports : "");
                })
                .call()
                .content();
    }
}