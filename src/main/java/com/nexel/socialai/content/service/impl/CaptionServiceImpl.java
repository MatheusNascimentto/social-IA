package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CaptionResponse;
import com.nexel.socialai.content.dto.CreateCaptionRequest;
import com.nexel.socialai.content.entity.Caption;
import com.nexel.socialai.content.mapper.CaptionMapper;
import com.nexel.socialai.content.repository.CaptionRepository;
import com.nexel.socialai.content.service.CaptionService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaptionServiceImpl implements CaptionService {

    private final CompanyRepository companyRepository;
    private final CaptionRepository captionRepository;
    private final AiProvider aiProvider;

    @Override
    public CaptionResponse generateAndSave(String requesterEmail, CreateCaptionRequest request) {
        log.info("Generating caption for companyId={} by requester={}", request.companyId(), requesterEmail);

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("Authenticated user does not have access to this company.");
        }

        String systemPrompt = buildSystemPrompt(company, request);
        String generated = aiProvider.generateContent(systemPrompt, request.prompt());

        Caption caption = Caption.builder()
                .company(company)
                .platform(request.platform())
                .contentType(request.contentType())
                .tone(request.tone())
                .content(generated)
                .build();

        Caption saved = captionRepository.save(caption);
        log.info("Caption saved successfully for companyId={} with id={}", company.getId(), saved.getId());

        return CaptionMapper.toResponse(saved);
    }

    private String buildSystemPrompt(Company company, CreateCaptionRequest request) {
        String tone = request.tone() == null || request.tone().isBlank() ? "professional and engaging" : request.tone();

        return "You are a senior social media strategist. "
                + "Write a " + request.contentType() + " for " + request.platform() + " using the following company context. "
                + "Company name: " + company.getName() + ". "
                + (company.getSegment() == null || company.getSegment().isBlank() ? "Company segment: not informed. " : "Company segment: " + company.getSegment() + ". ")
                + "Tone: " + tone + ". "
                + "Answer only with the final content and keep the text ready to publish.";
    }
}
