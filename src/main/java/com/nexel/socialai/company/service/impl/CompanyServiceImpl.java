package com.nexel.socialai.company.service.impl;

import com.nexel.socialai.company.dto.CompanyResponse;
import com.nexel.socialai.company.dto.CreateCompanyRequest;
import com.nexel.socialai.company.dto.UpdateCompanyRequest;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.mapper.CompanyMapper;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.company.service.CompanyService;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.user.entity.User;
import com.nexel.socialai.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CompanyResponse create(String ownerEmail, CreateCompanyRequest request) {
        User owner = userRepository.findByEmail(ownerEmail.trim())
                .orElseThrow(() -> new BusinessException("Owner not found."));
        Company company = CompanyMapper.toEntity(request, owner);
        Company saved = companyRepository.save(company);
        return CompanyMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse findById(String requesterEmail, UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException("Company not found."));
        // ownership: requester must be owner
        if (!company.getOwner().getEmail().equalsIgnoreCase(requesterEmail.trim())) {
            throw new BusinessException("You are not the owner of this company.");
        }
        return CompanyMapper.toResponse(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyResponse> listByOwner(String ownerEmail, Pageable pageable) {
        User owner = userRepository.findByEmail(ownerEmail.trim())
                .orElseThrow(() -> new BusinessException("Owner not found."));
        Page<Company> page = companyRepository.findByOwnerId(owner.getId(), pageable);
        return new PageImpl<>(page.map(CompanyMapper::toResponse).toList(), pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public CompanyResponse update(String requesterEmail, UUID companyId, UpdateCompanyRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException("Company not found."));
        if (!company.getOwner().getEmail().equalsIgnoreCase(requesterEmail.trim())) {
            throw new BusinessException("You are not the owner of this company.");
        }
        if (request.name() != null && !request.name().isBlank()) {
            company.setName(request.name());
        }
        if (request.segment() != null) {
            company.setSegment(request.segment());
        }
        Company saved = companyRepository.save(company);
        return CompanyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(String requesterEmail, UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException("Company not found."));
        if (!company.getOwner().getEmail().equalsIgnoreCase(requesterEmail.trim())) {
            throw new BusinessException("You are not the owner of this company.");
        }
        companyRepository.delete(company);
    }
}
