package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CreateHashtagRequest;
import com.nexel.socialai.content.dto.HashtagResponse;
import com.nexel.socialai.content.entity.Hashtag;
import com.nexel.socialai.content.mapper.HashtagMapper;
import com.nexel.socialai.content.repository.HashtagRepository;
import com.nexel.socialai.content.service.HashtagService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final CompanyRepository companyRepository;
    private final HashtagRepository hashtagRepository;
    private final AiProvider aiProvider;

    @Override
    public HashtagResponse generateAndSave(String requesterEmail, CreateHashtagRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("Authenticated user does not have access to this company.");
        }

        String systemPrompt = buildSystemPrompt(company);
        // ask AI to return comma-separated hashtags
        String aiOutput = aiProvider.generateContent(systemPrompt, request.prompt() + "\n\nRespond with a comma-separated list of hashtags (include the # symbol). Example: #java, #springboot, #backend");

        // normalize: ensure commas exist; if AI returns newlines, replace with commas
        String normalized = aiOutput.trim().replaceAll("\\s*\n+\\s*", ", ");

        Hashtag hashtag = Hashtag.builder()
                .company(company)
                .tags(normalized)
                .build();

        Hashtag saved = hashtagRepository.save(hashtag);

        return HashtagMapper.toResponse(saved);
    }

    private String buildSystemPrompt(Company company) {
        return "You are a senior social media strategist. Generate relevant and popular hashtags for the content based on the company context. "
                + "Company name: " + company.getName() + ". "
                + (company.getSegment() == null || company.getSegment().isBlank() ? "Company segment: not informed. " : "Company segment: " + company.getSegment() + ". ")
                + "Return only the hashtags as a comma-separated list, use the # symbol before each tag, and avoid explanations.";
    }
}
