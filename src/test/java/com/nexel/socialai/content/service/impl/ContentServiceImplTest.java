package com.nexel.socialai.content.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.GenerateContentRequest;
import com.nexel.socialai.content.dto.GeneratedContentResponse;
import com.nexel.socialai.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ContentServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private AiProvider aiProvider;

    @InjectMocks
    private ContentServiceImpl contentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateContentForCompanyOwner() {
        UUID companyId = UUID.randomUUID();
        User owner = User.builder().email("owner@example.com").build();
        Company company = Company.builder()
                .id(companyId)
                .owner(owner)
                .name("Nexel Labs")
                .segment("Technology")
                .build();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(aiProvider.generateContent(anyString(), eq("Create a caption for our weekend offer.")))
                .thenReturn("Here is a polished caption for your post.");

        GeneratedContentResponse response = contentService.generate("owner@example.com",
                new GenerateContentRequest(companyId, "INSTAGRAM", "CAPTION", "Create a caption for our weekend offer.", "professional"));

        assertThat(response.companyId()).isEqualTo(companyId);
        assertThat(response.companyName()).isEqualTo("Nexel Labs");
        assertThat(response.content()).isEqualTo("Here is a polished caption for your post.");
    }
}
