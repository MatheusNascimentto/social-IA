package com.nexel.socialai.content.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.content.dto.HistoryResponse;
import com.nexel.socialai.content.entity.HistoryEntry;
import com.nexel.socialai.content.repository.HistoryRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Test
    void shouldListHistoryByCompany() {
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder().id(companyId).name("Nexel Labs").build();
        HistoryEntry entry = HistoryEntry.builder()
                .id(UUID.randomUUID())
                .company(company)
                .contentType("CAPTION")
                .content("Test content")
                .createdAt(Instant.now())
                .build();

        when(historyRepository.findByCompanyId(companyId)).thenReturn(List.of(entry));

        List<HistoryResponse> result = historyService.listByCompany(companyId);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().companyId()).isEqualTo(companyId);
        assertThat(result.getFirst().contentType()).isEqualTo("CAPTION");
    }
}
