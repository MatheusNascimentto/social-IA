package com.nexel.socialai.ai.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.ai.provider.OllamaProviderImpl;
import com.nexel.socialai.ai.provider.OpenAiProviderImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class AiProviderConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(OpenAiWebClientConfig.class, AiProviderConfig.class));

    @Test
    void shouldUseOpenAiProviderByDefault() {
        contextRunner
                .withPropertyValues(
                        "app.ai.provider=openai",
                        "app.ai.openai.api-key=test-api-key",
                        "app.ai.openai.base-url=https://api.openai.com/v1",
                        "app.ai.openai.model=gpt-4o-mini",
                        "app.ai.openai.timeout-seconds=30"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(AiProvider.class);
                    assertThat(context.getBean(AiProvider.class)).isInstanceOf(OpenAiProviderImpl.class);
                });
    }

    @Test
    void shouldUseOllamaProviderWhenConfigured() {
        contextRunner
                .withPropertyValues(
                        "app.ai.provider=ollama",
                        "app.ai.ollama.base-url=http://localhost:11434",
                        "app.ai.ollama.model=llama3.2:latest",
                        "app.ai.ollama.timeout-seconds=120"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(AiProvider.class);
                    assertThat(context.getBean(AiProvider.class)).isInstanceOf(OllamaProviderImpl.class);
                });
    }
}
