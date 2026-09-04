package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CreateReelRequest;
import com.nexel.socialai.content.dto.ReelResponse;
import com.nexel.socialai.content.entity.ReelScript;
import com.nexel.socialai.content.mapper.ReelScriptMapper;
import com.nexel.socialai.content.repository.ReelScriptRepository;
import com.nexel.socialai.content.service.ReelService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReelServiceImpl implements ReelService {

    private final CompanyRepository companyRepository;
    private final ReelScriptRepository reelScriptRepository;
    private final AiProvider aiProvider;

    @Override
    public ReelResponse generateAndSave(String requesterEmail, CreateReelRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("Authenticated user does not have access to this company.");
        }

        int steps = request.steps() == null ? 6 : request.steps();

        String systemPrompt = buildSystemPrompt(company);
        String aiOutput = aiProvider.generateContent(systemPrompt, request.prompt() + "\n\nRespond with a numbered list of " + steps + " short script steps for a Reel, each on its own line.");

        String normalized = aiOutput.trim().replaceAll("\\r?\\n\\s*\\r?\\n+", "\n");

        ReelScript script = ReelScript.builder()
                .company(company)
                .script(normalized)
                .build();

        ReelScript saved = reelScriptRepository.save(script);

        return ReelScriptMapper.toResponse(saved);
    }

    private String buildSystemPrompt(Company company) {
        return "You are a senior social media strategist. Create a short, engaging step-by-step script for a social media Reels video using the company context. "
                + "Company name: " + company.getName() + ". "
                + (company.getSegment() == null || company.getSegment().isBlank() ? "Company segment: not informed. " : "Company segment: " + company.getSegment() + ". ")
                + "Return only a numbered list; each step should be concise and include suggested actions or captions for the clip.";
    }
}
