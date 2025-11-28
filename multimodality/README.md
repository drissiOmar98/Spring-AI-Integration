# 🎨 Spring AI – Multimodality Starter Kit
## Image → Text • Text → Image • Text → Speech (TTS)

This project demonstrates **true multimodal AI capabilities** using **Spring AI**, showcasing how modern AI models can understand and generate **images, audio, and text** seamlessly within a Spring Boot application.

It includes three fully functional controllers:

- 📸 **Image Detection** → AI describes an uploaded image (vision model)
- 🖼️ **Image Generation** → Generate HD images using DALL-E 3
- 🔊 **Audio Generation (TTS)** → Convert text into realistic MP3 speech

Perfect for developers exploring multimodal AI, creative automation, accessibility features, and next-generation Spring Boot applications.

---

# 🚀 Features at a Glance

| Capability | Endpoint | Description |
|-----------|----------|-------------|
| **Image → Text** | `/image-to-text` | Sends an image to an LLM and receives a visual description |
| **Text → Image** | `/generate-image` | Creates an HD, vivid-style DALL-E 3 image based on a text prompt |
| **Text → Speech (TTS)** | `/speak` | Generates MP3 audio from text using OpenAI TTS-1-HD |

---

# 🔥 Why Multimodality Matters

Modern applications need to go beyond simple text responses.

With Spring AI, you can implement:

- **Visual search systems**
- **Accessibility tools (image captioning, voice generation)**
- **Content generation at scale (images, voice clips, alt text)**
- **Digital assistants that SEE, SPEAK, and CREATE**
- **Media analysis & content moderation**

Multimodality unlocks a complete AI-powered experience—and this project shows how.

---

# 📸 Image → Text (Vision Model)
## AI-Powered Image Understanding

The Image-to-Text controller uses a multimodal LLM capable of interpreting visual input and returning a human-like description.

### 🔍 How It Works
1. You send an image (JPEG/PNG).
2. Spring AI wraps it into a multimodal prompt.
3. The model analyzes objects, context, environment, style, and composition.
4. It returns a rich, natural-language explanation.

### 🌟 Use Cases
- Auto-captioning for accessibility
- Product image analysis
- Social media content generation
- Automated documentation
- Vision-driven assistants  

# 🖼️ Text → Image (DALL-E 3 HD)
## High-Resolution Image Generation

The Text-to-Image controller brings creative image generation to your Spring Boot app, powered by DALL-E 3.

### ⚡ Features
- 1024×1024 HD output
- “Vivid” creative rendering style
- Stable, repeatable prompt processing
- JSON metadata with generated image URL  

### 🎨 Example Output
Returns a URL pointing to an HD AI-generated image.

### 🌟 Use Cases
- Marketing & branding visuals
- Fast UI concept art
- Storyboarding + creative ideation
- Blog / article illustrations
- Product mockups  

# 🔊 Text → Speech (TTS)
## Lifelike Voice Generation (MP3 Output)

The Text-to-Speech controller uses the **TTS-1-HD** model for natural, expressive speech in MP3 format.

### 🎤 Voice Options
- ALLOY (default)
- NOVA
- ONYX
- FABLE
- ECHO
- SHIMMER

### 🔧 What You Can Customize
- Voice selection
- Speaking speed
- Output format (MP3/WAV)
- Raw byte streaming

### 🌟 Use Cases
- Voice-enabled chatbots
- Narration for educational platforms
- Audio guides & accessibility
- Automated podcast snippets
- Interactive assistants  

---

## 📋 Requirements

Ensure you have the following installed:

- **Java 21+**
- **Maven or Gradle**
- **API key** for your chosen AI provider  
  (OpenAI-compatible LLMs or local model via Spring AI)

---

## ⚙️ Configuration

Configure your AI provider and API key using environment variables.  
Example for OpenAI:

```bash
export OPENAI_API_KEY=YOUR_TOKEN_VALUE_HERE