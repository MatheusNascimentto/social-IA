package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public record GeneratedContentResponse(
        @Schema(description = "Company identifier used to generate the content")
        UUID companyId,

        @Schema(description = "Company name used as context")
        String companyName,

        @Schema(description = "Platform requested")
        String platform,

        @Schema(description = "Requested content type")
        String contentType,

        @Schema(description = "Generated content returned by the AI")
        String content,

        @Schema(description = "Generation timestamp")
        Instant generatedAt
) {
}
