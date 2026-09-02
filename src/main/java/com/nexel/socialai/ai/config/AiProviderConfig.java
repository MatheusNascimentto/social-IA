package com.nexel.socialai.ai.config;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.ai.provider.OllamaProviderImpl;
import com.nexel.socialai.ai.provider.OpenAiProviderImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AiProviderConfig {

    @Bean
    @ConditionalOnProperty(name = "app.ai.provider", havingValue = "openai", matchIfMissing = true)
    public AiProvider openAiProvider(
            @Qualifier("openAiWebClient") WebClient webClient,
            @Value("${app.ai.openai.model:gpt-4o-mini}") String model,
            @Value("${app.ai.openai.timeout-seconds:30}") long timeoutSeconds
    ) {
        return new OpenAiProviderImpl(webClient, model, timeoutSeconds);
    }

    @Bean
    @ConditionalOnProperty(name = "app.ai.provider", havingValue = "ollama")
    public AiProvider ollamaProvider(
            @Qualifier("ollamaWebClient") WebClient webClient,
            @Value("${app.ai.ollama.model:llama3.2:latest}") String model,
            @Value("${app.ai.ollama.timeout-seconds:120}") long timeoutSeconds
    ) {
        return new OllamaProviderImpl(webClient, model, timeoutSeconds);
    }
}
