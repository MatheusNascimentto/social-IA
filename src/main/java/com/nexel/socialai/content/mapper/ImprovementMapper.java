package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.ImprovementResponse;
import com.nexel.socialai.content.entity.Improvement;

public final class ImprovementMapper {

    private ImprovementMapper() { }

    public static ImprovementResponse toResponse(Improvement e) {
        return new ImprovementResponse(e.getId(), e.getCompany().getId(), e.getOriginalContent(), e.getImprovedContent(), e.getCreatedAt());
    }
}
