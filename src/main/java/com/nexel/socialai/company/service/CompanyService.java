package com.nexel.socialai.company.service;

import com.nexel.socialai.company.dto.CompanyResponse;
import com.nexel.socialai.company.dto.CreateCompanyRequest;
import com.nexel.socialai.company.dto.UpdateCompanyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompanyService {

    CompanyResponse create(String ownerEmail, CreateCompanyRequest request);

    CompanyResponse findById(String requesterEmail, UUID companyId);

    Page<CompanyResponse> listByOwner(String ownerEmail, Pageable pageable);

    CompanyResponse update(String requesterEmail, UUID companyId, UpdateCompanyRequest request);

    void delete(String requesterEmail, UUID companyId);
}
