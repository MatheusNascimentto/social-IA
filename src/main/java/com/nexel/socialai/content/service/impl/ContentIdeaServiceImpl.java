package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CreateContentIdeaRequest;
import com.nexel.socialai.content.dto.ContentIdeaResponse;
import com.nexel.socialai.content.entity.ContentIdea;
import com.nexel.socialai.content.mapper.ContentIdeaMapper;
import com.nexel.socialai.content.repository.ContentIdeaRepository;
import com.nexel.socialai.content.service.ContentIdeaService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentIdeaServiceImpl implements ContentIdeaService {

    private final CompanyRepository companyRepository;
    private final ContentIdeaRepository contentIdeaRepository;
    private final AiProvider aiProvider;

    @Override
    public ContentIdeaResponse generateAndSave(String requesterEmail, CreateContentIdeaRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("Authenticated user does not have access to this company.");
        }

        int count = request.count() == null ? 5 : request.count();

        String systemPrompt = buildSystemPrompt(company);
        String aiOutput = aiProvider.generateContent(systemPrompt, request.prompt() + "\n\nRespond with a numbered list of " + count + " short content ideas, each on its own line.");

        // normalize: replace multiple blank lines and ensure newline separators
        String normalized = aiOutput.trim().replaceAll("\\r?\\n\\s*\\r?\\n+", "\n");

        ContentIdea idea = ContentIdea.builder()
                .company(company)
                .ideas(normalized)
                .build();

        ContentIdea saved = contentIdeaRepository.save(idea);

        return ContentIdeaMapper.toResponse(saved);
    }

    private String buildSystemPrompt(Company company) {
        return "You are a senior social media strategist. Generate short content ideas for social media based on the company context. "
                + "Company name: " + company.getName() + ". "
                + (company.getSegment() == null || company.getSegment().isBlank() ? "Company segment: not informed. " : "Company segment: " + company.getSegment() + ". ")
                + "Return only a numbered list; each idea must be short and publish-ready.";
    }
}
