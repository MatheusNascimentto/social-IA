package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public record HistoryResponse(
        @Schema(description = "History entry id") UUID id,
        @Schema(description = "Company id") UUID companyId,
        @Schema(description = "Content type") String contentType,
        @Schema(description = "Reference id (if any)") UUID referenceId,
        @Schema(description = "Raw content") String content,
        @Schema(description = "Creation timestamp") Instant createdAt
) {}
