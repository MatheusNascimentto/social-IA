package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public record CaptionResponse(
        @Schema(description = "Caption id") UUID id,
        @Schema(description = "Company id") UUID companyId,
        @Schema(description = "Platform") String platform,
        @Schema(description = "Content type") String contentType,
        @Schema(description = "Tone") String tone,
        @Schema(description = "Generated caption content") String content,
        @Schema(description = "Creation timestamp") Instant createdAt
) {
}
