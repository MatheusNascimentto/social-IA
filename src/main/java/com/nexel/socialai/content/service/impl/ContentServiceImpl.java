package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.GenerateContentRequest;
import com.nexel.socialai.content.dto.GeneratedContentResponse;
import com.nexel.socialai.content.service.ContentService;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentServiceImpl implements ContentService {

    private final CompanyRepository companyRepository;
    private final AiProvider aiProvider;

    @Override
    public GeneratedContentResponse generate(String requesterEmail, GenerateContentRequest request) {
        log.info("Generating content for companyId={} by requester={}", request.companyId(), requesterEmail);

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("The authenticated user does not have access to this company.");
        }

        String systemPrompt = buildSystemPrompt(company, request);
        String generatedContent = aiProvider.generateContent(systemPrompt, request.prompt());
        log.info("Content generated successfully for companyId={}", company.getId());

        return new GeneratedContentResponse(
                company.getId(),
                company.getName(),
                request.platform(),
                request.contentType(),
                generatedContent,
                Instant.now()
        );
    }

    private String buildSystemPrompt(Company company, GenerateContentRequest request) {
        String tone = request.tone() == null || request.tone().isBlank() ? "professional and engaging" : request.tone();

        return "You are a senior social media strategist. "
                + "Write content for " + request.platform() + " using the following company context. "
                + "Company name: " + company.getName() + ". "
                + (company.getSegment() == null || company.getSegment().isBlank() ? "Company segment: not informed. " : "Company segment: " + company.getSegment() + ". ")
                + "Tone: " + tone + ". "
                + "Answer only with the final content for the requested format and keep the text ready to publish.";
    }
}
