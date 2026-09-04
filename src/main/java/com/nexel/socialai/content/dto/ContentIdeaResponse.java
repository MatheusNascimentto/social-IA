package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ContentIdeaResponse(
        @Schema(description = "Content idea id") UUID id,
        @Schema(description = "Company id") UUID companyId,
        @Schema(description = "List of generated ideas") List<String> ideas,
        @Schema(description = "Creation timestamp") Instant createdAt
) {
}
