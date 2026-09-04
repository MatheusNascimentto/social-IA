package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.CaptionResponse;
import com.nexel.socialai.content.entity.Caption;

public final class CaptionMapper {

    private CaptionMapper() {}

    public static CaptionResponse toResponse(Caption caption) {
        return new CaptionResponse(
                caption.getId(),
                caption.getCompany().getId(),
                caption.getPlatform(),
                caption.getContentType(),
                caption.getTone(),
                caption.getContent(),
                caption.getCreatedAt()
        );
    }
}
