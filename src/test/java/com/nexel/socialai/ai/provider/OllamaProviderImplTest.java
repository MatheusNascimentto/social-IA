package com.nexel.socialai.ai.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nexel.socialai.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class OllamaProviderImplTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private OllamaProviderImpl provider;

    @BeforeEach
    void setUp() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/api/chat")).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        provider = new OllamaProviderImpl(webClient, "llama3.2:latest", 120L);
    }

    @Test
    void shouldGenerateContentFromOllamaResponse() {
        when(responseSpec.bodyToMono(OllamaProviderImpl.OllamaChatResponse.class))
                .thenReturn(Mono.just(new OllamaProviderImpl.OllamaChatResponse(
                        new OllamaProviderImpl.OllamaChatResponse.Message("Here is the generated caption."))));

        String content = provider.generateContent("You are a social strategist.", "Create a caption.");

        assertThat(content).isEqualTo("Here is the generated caption.");
    }

    @Test
    void shouldThrowBusinessExceptionWhenOllamaResponseIsEmpty() {
        when(responseSpec.bodyToMono(OllamaProviderImpl.OllamaChatResponse.class))
                .thenReturn(Mono.just(new OllamaProviderImpl.OllamaChatResponse(null)));

        assertThatThrownBy(() -> provider.generateContent("You are a social strategist.", "Create a caption."))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("empty response");
    }
}
