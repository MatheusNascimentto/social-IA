package com.nexel.socialai.ai.provider;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import java.time.Duration;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OllamaProviderImpl implements AiProvider {

    private final WebClient webClient;
    private final String model;
    private final Duration timeout;

    public OllamaProviderImpl(WebClient ollamaWebClient, String model, long timeoutSeconds) {
        this.webClient = ollamaWebClient;
        this.model = model;
        this.timeout = Duration.ofSeconds(timeoutSeconds);
    }

    @Override
    public String generateContent(String systemPrompt, String userPrompt) {
        if (systemPrompt == null || systemPrompt.isBlank()) {
            throw new BusinessException("The AI system prompt cannot be empty.");
        }

        if (userPrompt == null || userPrompt.isBlank()) {
            throw new BusinessException("The content prompt cannot be empty.");
        }

        OllamaChatRequest request = new OllamaChatRequest(
                model,
                List.of(
                        new OllamaChatRequest.Message("system", systemPrompt),
                        new OllamaChatRequest.Message("user", userPrompt)
                ),
                false
        );

        OllamaChatResponse response = webClient.post()
                .uri("/api/chat")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("Invalid Ollama request.")
                                .flatMap(body -> Mono.error(new BusinessException("Ollama request failed: " + body))))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("Ollama service unavailable.")
                                .flatMap(body -> Mono.error(new BusinessException("Ollama service error: " + body))))
                .bodyToMono(OllamaChatResponse.class)
                .timeout(timeout)
                .block();

        if (response == null || response.message() == null || response.message().content() == null || response.message().content().isBlank()) {
            throw new BusinessException("The Ollama provider returned an empty response.");
        }

        return response.message().content().trim();
    }

    static record OllamaChatRequest(String model, List<Message> messages, boolean stream) {
        static record Message(String role, String content) {
        }
    }

    static record OllamaChatResponse(Message message) {
        static record Message(String content) {
        }
    }
}
