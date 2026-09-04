package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.ai.client.AiProvider;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.content.dto.CreateContentCalendarRequest;
import com.nexel.socialai.content.dto.ContentCalendarResponse;
import com.nexel.socialai.content.entity.ContentCalendar;
import com.nexel.socialai.content.mapper.ContentCalendarMapper;
import com.nexel.socialai.content.repository.ContentCalendarRepository;
import com.nexel.socialai.content.service.ContentCalendarService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentCalendarServiceImpl implements ContentCalendarService {

    private final CompanyRepository companyRepository;
    private final ContentCalendarRepository contentCalendarRepository;
    private final AiProvider aiProvider;

    @Override
    public ContentCalendarResponse generateAndSave(String requesterEmail, CreateContentCalendarRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessException("Company not found."));

        if (!Objects.equals(company.getOwner().getEmail(), requesterEmail.trim())) {
            throw new BusinessException("Authenticated user does not have access to this company.");
        }

        LocalDate start = request.startDate() == null ? LocalDate.now() : request.startDate();
        int days = request.days() == null ? 14 : request.days();

        String systemPrompt = buildSystemPrompt(company);
        String aiPrompt = request.prompt() + "\n\nRespond with a numbered list of " + days + " entries. Each entry must start with an ISO date starting at " + start.format(DateTimeFormatter.ISO_DATE) + ", incrementing by one day, followed by ": " + "and then a short content idea. Example:\n2026-09-01: Short caption about X";

        String aiOutput = aiProvider.generateContent(systemPrompt, aiPrompt);

        String normalized = aiOutput.trim().replaceAll("\\r?\\n\\s*\\r?\\n+", "\n");

        ContentCalendar calendar = ContentCalendar.builder()
                .company(company)
                .entries(normalized)
                .build();

        ContentCalendar saved = contentCalendarRepository.save(calendar);

        return ContentCalendarMapper.toResponse(saved);
    }

    private String buildSystemPrompt(Company company) {
        return "You are a senior social media strategist. Generate a short content calendar tailored to the company context. "
                + "Company name: " + company.getName() + ". "
                + (company.getSegment() == null || company.getSegment().isBlank() ? "Company segment: not informed. " : "Company segment: " + company.getSegment() + ". ")
                + "Return only the dated list entries, one per line, in the format 'YYYY-MM-DD: idea'. Avoid extra text.";
    }
}
