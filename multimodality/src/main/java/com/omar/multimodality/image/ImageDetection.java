package com.omar.multimodality.image;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 🔹 Image Detection Endpoint
 * <p>
 * Sends an image to the AI model and returns a textual description.
 * Demonstrates multimodal capabilities with Spring AI (image → text).
 * <p>
 * Workflow:
 *   1. Loads a sample image from the classpath (`sampleImage`).
 *   2. Sends the image to the AI model with a prompt asking to explain what is seen.
 *   3. Returns the AI-generated textual description.
 * <p>
 * Use Case:
 *   - Image understanding or caption generation for applications such as
 *     accessibility, media analysis, or content moderation.
 *   - Demonstrates integration of **image input with Spring AI LLMs**.
 * <p>
 * Endpoint: GET /image-to-text
 * Returns:
 *   - A String containing the AI’s textual interpretation of the image.
 */
@RestController
public class ImageDetection {

    private final ChatClient chatClient;
    @Value("classpath:/images/sincerely-media-2UlZpdNzn2w-unsplash.jpg")


    Resource sampleImage;

    public ImageDetection(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/image-to-text")
    public String image() throws IOException {
        return chatClient.prompt()
                .user(u -> u
                        .text("Can you please explain what you see in the following image?")
                        .media(MimeTypeUtils.IMAGE_JPEG,sampleImage)
                )
                .call()
                .content();
    }
}
