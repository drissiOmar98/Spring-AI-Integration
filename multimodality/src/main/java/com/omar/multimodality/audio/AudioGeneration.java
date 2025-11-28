package com.omar.multimodality.audio;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 🔹 Audio Generation Controller
 * <p>
 * Provides an endpoint to generate speech audio from text prompts using OpenAI's TTS model.
 * Users can specify a text string, and the AI returns an MP3 audio file of the spoken text.
 * <p>
 * Endpoint: GET /speak
 * Query Parameter:
 *   - text (optional): Text to convert to speech. Default: "It's a great time to be a Java & Spring Developer!".
 * <p>
 * Use Case:
 * - Generate speech for accessibility, voice assistants, educational content, or any application requiring TTS output.
 * - Supports multiple voices (ALLOY, ECHO, FABLE, ONYX, NOVA, SHIMMER) and adjustable speed.
 * - Returns the audio file as an MP3 attachment in the HTTP response.
 */
@RestController
public class AudioGeneration {

    /**
     * Spring AI OpenAI audio speech model client for interacting with TTS API.
     * Injected via constructor for immutability and testability.
     */
    private final OpenAiAudioSpeechModel audioSpeechModel;

    /**
     * Constructor for dependency injection of the OpenAI TTS model.
     *
     * @param audioSpeechModel the OpenAI audio speech model client, automatically wired by Spring
     */
    public AudioGeneration(OpenAiAudioSpeechModel audioSpeechModel) {
        this.audioSpeechModel = audioSpeechModel;
    }


    /**
     * Converts text to speech using OpenAI's TTS models and returns MP3 audio.
     * <p>
     * This endpoint accepts text input and returns an MP3 audio stream of the synthesized speech.
     * Uses high-quality TTS-1-HD model with configurable voice and speech parameters.
     * @param text the text content to convert to speech
     *             (default: "It's a great time to be a Java & Spring Developer!")
     * @return ResponseEntity containing MP3 audio bytes with appropriate audio headers
     *
     * @apiNote This endpoint uses OpenAI TTS which has specific characteristics:
     *          - TTS-1-HD provides highest quality speech synthesis
     *          - Multiple voice options available (ALLOY, ECHO, FABLE, ONYX, NOVA, SHIMMER)
     *          - Speech speed adjustable from 0.25x to 4.0x normal speed
     *          - Output format fixed to MP3 for broad compatibility
     *          - API costs apply based on character count
     */
    @GetMapping("/speak")
    public ResponseEntity<byte[]> generateSpeech(
            @RequestParam(defaultValue = "It's a great time to be a Java & Spring Developer!") String text) {

        // Configure TTS options for high-quality speech synthesis
        var options = OpenAiAudioSpeechOptions.builder()
                .model("tts-1-hd")  // High-definition text-to-speech model
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY) // ALLOY, ECHO, FABLE, ONYX, NOVA, SHIMMER
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(1.0)  // Normal speech speed (range: 0.25 to 4.0)
                .build();

        // Create the text-to-speech prompt with user input and configured options
        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(text, options);

        // Call OpenAI API to generate speech audio
        TextToSpeechResponse response = audioSpeechModel.call(speechPrompt);

        // Extract the generated audio bytes from the response
        byte[] audioBytes = response.getResult().getOutput();

        // Return audio stream with proper headers for browser playback/download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg") // MP3 content type
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"") // Download filename
                .body(audioBytes);
    }

}