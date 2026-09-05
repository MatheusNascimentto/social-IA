package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CreateImprovementRequest;
import com.nexel.socialai.content.dto.ImprovementResponse;
import com.nexel.socialai.content.entity.HistoryEntry;
import com.nexel.socialai.content.entity.Improvement;
import com.nexel.socialai.content.mapper.ImprovementMapper;
import com.nexel.socialai.content.repository.HistoryRepository;
import com.nexel.socialai.content.repository.ImprovementRepository;
import com.nexel.socialai.content.service.ImprovementService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImprovementServiceImpl implements ImprovementService {

    private final CompanyRepository companyRepository;
    private final ImprovementRepository improvementRepository;
    private final HistoryRepository historyRepository;
    private final AiProvider aiProvider;

    @Override
    public ImprovementResponse improveAndSave(String requesterEmail, CreateImprovementRequest request) {
        log.info("Improving content for companyId={} by requester={}", request.companyId(), requesterEmail);

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("Authenticated user does not have access to this company.");
        }

        String instruction = request.prompt() == null || request.prompt().isBlank()
                ? "Improve or rewrite the provided content keeping the original intent; return only the improved content." : request.prompt();

        String aiOutput = aiProvider.generateContent("You are a senior copywriter. " + "Company: " + company.getName() + ".", request.content() + "\n\n" + instruction);

        String normalized = aiOutput.trim();

        Improvement improvement = Improvement.builder()
                .company(company)
                .originalContent(request.content())
                .improvedContent(normalized)
                .build();

        Improvement saved = improvementRepository.save(improvement);

        HistoryEntry entry = HistoryEntry.builder()
                .company(company)
                .contentType("IMPROVEMENT")
                .referenceId(saved.getId())
                .content(normalized)
                .build();

        historyRepository.save(entry);
        log.info("Improvement saved successfully for companyId={} with id={}", company.getId(), saved.getId());

        return ImprovementMapper.toResponse(saved);
    }
}
