package com.nexel.socialai.company.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nexel.socialai.company.dto.CompanyResponse;
import com.nexel.socialai.company.dto.CreateCompanyRequest;
import com.nexel.socialai.company.dto.UpdateCompanyRequest;
import com.nexel.socialai.company.service.CompanyService;
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

class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
    }

    @Test
    void shouldCreateCompany() throws Exception {
        UUID companyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        CompanyResponse response = new CompanyResponse(companyId, ownerId, "Nexel Labs", "Technology", Instant.now(), Instant.now());
        CreateCompanyRequest request = new CreateCompanyRequest("Nexel Labs", "Technology");

        when(companyService.create("owner@example.com", request)).thenReturn(response);

        mockMvc.perform(post("/companies")
                        .principal(() -> "owner@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nexel Labs\",\"segment\":\"Technology\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nexel Labs"));
    }

    @Test
    void shouldGetCompanyById() throws Exception {
        UUID companyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        CompanyResponse response = new CompanyResponse(companyId, ownerId, "Nexel Labs", "Technology", Instant.now(), Instant.now());

        when(companyService.findById("owner@example.com", companyId)).thenReturn(response);

        mockMvc.perform(get("/companies/{id}", companyId)
                        .principal(() -> "owner@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nexel Labs"));
    }

    @Test
    void shouldUpdateCompany() throws Exception {
        UUID companyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        CompanyResponse response = new CompanyResponse(companyId, ownerId, "Updated Company", "Marketing", Instant.now(), Instant.now());
        UpdateCompanyRequest request = new UpdateCompanyRequest("Updated Company", "Marketing");

        when(companyService.update("owner@example.com", companyId, request)).thenReturn(response);

        mockMvc.perform(put("/companies/{id}", companyId)
                        .principal(() -> "owner@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Company\",\"segment\":\"Marketing\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Company"));
    }

    @Test
    void shouldDeleteCompany() throws Exception {
        UUID companyId = UUID.randomUUID();

        mockMvc.perform(delete("/companies/{id}", companyId)
                        .principal(() -> "owner@example.com"))
                .andExpect(status().isNoContent());
    }
}
