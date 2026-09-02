package com.nexel.socialai.company.mapper;

import com.nexel.socialai.company.dto.CompanyResponse;
import com.nexel.socialai.company.dto.CreateCompanyRequest;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.user.entity.User;
import java.util.UUID;

public final class CompanyMapper {

    private CompanyMapper() {}

    public static Company toEntity(CreateCompanyRequest request, User owner) {
        return Company.builder()
                .owner(owner)
                .name(request.name())
                .segment(request.segment())
                .build();
    }

    public static CompanyResponse toResponse(Company company) {
        UUID ownerId = company.getOwner() != null ? company.getOwner().getId() : null;
        return new CompanyResponse(
                company.getId(),
                ownerId,
                company.getName(),
                company.getSegment(),
                company.getCreatedAt(),
                company.getUpdatedAt()
        );
    }
}
