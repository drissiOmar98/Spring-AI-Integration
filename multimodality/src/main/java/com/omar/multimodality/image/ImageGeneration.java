package com.omar.multimodality.image;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 🔹 Image Generation Controller
 * <p>
 * Provides an endpoint to generate images from text prompts using the OpenAI DALL-E model.
 * Users can specify a prompt, and the AI returns a high-quality, styled image URL.
 * <p>
 * Endpoint: GET /generate-image
 * Query Parameter:
 *   - prompt (optional): Text description of the desired image. Default: "A beautiful sunset over mountains".
 * <p>
 * Example Response:
 * {
 *   "prompt": "A beautiful sunset over mountains",
 *   "imageUrl": "https://..."
 * }
 * <p>
 * Use Case:
 * - Generate images for creative content, social media, or app visuals programmatically.
 */
@RestController
public class ImageGeneration {

    /**
     * Spring AI OpenAI image model client for interacting with DALL-E API.
     * Injected via constructor injection for better testability and immutability.
     */
    private final OpenAiImageModel imageModel;

    /**
     * Constructor for dependency injection of the OpenAI image model.
     *
     * @param imageModel the OpenAI image model client, automatically wired by Spring
     */
    public ImageGeneration(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }


    /**
     * Generates an image from a text prompt using OpenAI's DALL-E 3 model.
     * <p>
     * This endpoint accepts a text description and returns a URL to the generated image.
     * The image is created with high-quality HD settings and vivid style by default.
     * <p>
     * Example usage:
     * GET /generate-image?prompt=A majestic dragon flying over a medieval castle
     *
     * @param prompt the text description for image generation (default: "A beautiful sunset over mountains")
     * @return ResponseEntity containing the original prompt and URL of the generated image
     *
     * @apiNote This endpoint uses DALL-E 3 which has specific limitations:
     *          - Maximum 1024x1024 resolution for square images
     *          - HD quality may consume more API credits
     *          - Rate limits apply based on OpenAI pricing tier
     */
    @GetMapping("/generate-image")
    public ResponseEntity<Map<String, String>> generateImage(
            @RequestParam(defaultValue = "A beautiful sunset over mountains") String prompt) {

        // Configure image generation options for DALL-E 3
        ImageOptions options = OpenAiImageOptions.builder()
                .model("dall-e-3")              // Use DALL-E 3 model for highest quality
                .width(1024)                    // Standard HD width
                .height(1024)                   // Standard HD height (square image)
                .quality("hd")                  // High definition quality
                .style("vivid")                 // Vibrant and dramatic style
                .build();

        // Create the image prompt with user input and configured options
        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);

        // Call OpenAI API to generate the image
        ImageResponse response = imageModel.call(imagePrompt);

        // Extract the generated image URL from the response
        String imageUrl = response.getResult().getOutput().getUrl();

        // Return prompt and image URL in response
        return ResponseEntity.ok(Map.of(
                "prompt", prompt,
                "imageUrl", imageUrl
        ));
    }

}