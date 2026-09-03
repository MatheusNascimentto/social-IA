package com.nexel.socialai.content.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nexel.socialai.content.dto.GenerateContentRequest;
import com.nexel.socialai.content.dto.GeneratedContentResponse;
import com.nexel.socialai.content.service.ContentService;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private ContentController contentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contentController).build();
    }

    @Test
    void shouldGenerateContent() throws Exception {
        UUID companyId = UUID.randomUUID();
        GenerateContentRequest request = new GenerateContentRequest(companyId, "INSTAGRAM", "CAPTION", "Create a caption for our weekend offer.", "professional");
        GeneratedContentResponse response = new GeneratedContentResponse(companyId, "Nexel Labs", "INSTAGRAM", "CAPTION",
                "Here is your polished caption.", Instant.now());

        when(contentService.generate(eq("owner@example.com"), any())).thenReturn(response);

        mockMvc.perform(post("/content/generate")
                        .principal(() -> "owner@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"companyId\":\"" + companyId + "\",\"platform\":\"INSTAGRAM\",\"contentType\":\"CAPTION\",\"prompt\":\"Create a caption for our weekend offer.\",\"tone\":\"professional\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Nexel Labs"))
                .andExpect(jsonPath("$.content").value("Here is your polished caption."));
    }
}
