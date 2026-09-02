package com.nexel.socialai.ai.client;

public interface AiProvider {

    String generateContent(String systemPrompt, String userPrompt);
}
