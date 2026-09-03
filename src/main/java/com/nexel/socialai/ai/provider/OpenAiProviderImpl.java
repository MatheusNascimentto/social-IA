package com.nexel.socialai.ai.provider;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OpenAiProviderImpl implements AiProvider {

    private final WebClient webClient;
    private final String model;
    private final Duration timeout;

    public OpenAiProviderImpl(
            WebClient openAiWebClient,
            @Value("${app.ai.openai.model:gpt-4o-mini}") String model,
            @Value("${app.ai.openai.timeout-seconds:30}") long timeoutSeconds
    ) {
        this.webClient = openAiWebClient;
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

        OpenAiChatCompletionRequest request = new OpenAiChatCompletionRequest(
                model,
                List.of(
                        new OpenAiChatCompletionRequest.Message("system", systemPrompt),
                        new OpenAiChatCompletionRequest.Message("user", userPrompt)
                ),
                0.7
        );

        OpenAiChatCompletionResponse response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("Invalid OpenAI request.")
                                .flatMap(body -> Mono.error(new BusinessException("OpenAI request failed: " + body))))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("OpenAI service unavailable.")
                                .flatMap(body -> Mono.error(new BusinessException("OpenAI service error: " + body))))
                .bodyToMono(OpenAiChatCompletionResponse.class)
                .timeout(timeout)
                .block();

        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            throw new BusinessException("The AI provider returned an empty response.");
        }

        OpenAiChatCompletionResponse.Choice firstChoice = response.choices().getFirst();
        if (firstChoice == null || firstChoice.message() == null || firstChoice.message().content() == null) {
            throw new BusinessException("The AI provider returned an invalid message payload.");
        }

        return firstChoice.message().content().trim();
    }

    private record OpenAiChatCompletionRequest(
            String model,
            List<Message> messages,
            Double temperature
    ) {
        private record Message(String role, String content) {
        }
    }

    private record OpenAiChatCompletionResponse(List<Choice> choices) {
        private record Choice(Message message) {
        }

        private record Message(String content) {
        }
    }
}
