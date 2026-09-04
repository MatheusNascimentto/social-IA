package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public record ImprovementResponse(
        @Schema(description = "Improvement id") UUID id,
        @Schema(description = "Company id") UUID companyId,
        @Schema(description = "Original content") String originalContent,
        @Schema(description = "Improved content") String improvedContent,
        @Schema(description = "Creation timestamp") Instant createdAt
) {}
