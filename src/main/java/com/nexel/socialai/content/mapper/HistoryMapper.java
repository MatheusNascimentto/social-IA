package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.HistoryResponse;
import com.nexel.socialai.content.entity.HistoryEntry;

public final class HistoryMapper {

    private HistoryMapper() { }

    public static HistoryResponse toResponse(HistoryEntry e) {
        return new HistoryResponse(e.getId(), e.getCompany().getId(), e.getContentType(), e.getReferenceId(), e.getContent(), e.getCreatedAt());
    }
}
