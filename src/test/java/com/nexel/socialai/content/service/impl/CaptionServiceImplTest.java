package com.nexel.socialai.content.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CaptionResponse;
import com.nexel.socialai.content.dto.CreateCaptionRequest;
import com.nexel.socialai.content.entity.Caption;
import com.nexel.socialai.content.repository.CaptionRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;

class CaptionServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CaptionRepository captionRepository;

    @Mock
    private AiProvider aiProvider;

    @InjectMocks
    private com.nexel.socialai.content.service.impl.CaptionServiceImpl captionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateAndSaveCaption() {
        UUID companyId = UUID.randomUUID();
        com.nexel.socialai.user.entity.User owner = com.nexel.socialai.user.entity.User.builder().email("owner@example.com").build();
        Company company = Company.builder().id(companyId).owner(owner).name("Nexel Labs").segment("Tech").build();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(aiProvider.generateContent(anyString(), eq("Create a caption"))).thenReturn("Generated caption text");
        when(captionRepository.save(any(Caption.class))).thenAnswer(inv -> {
            Caption c = inv.getArgument(0);
            c.setId(UUID.randomUUID());
            return c;
        });

        CaptionResponse response = captionService.generateAndSave("owner@example.com", new CreateCaptionRequest(companyId, "INSTAGRAM", "CAPTION", "Create a caption", "professional"));

        assertThat(response).isNotNull();
        assertThat(response.content()).isEqualTo("Generated caption text");
        assertThat(response.companyId()).isEqualTo(companyId);
    }
}
