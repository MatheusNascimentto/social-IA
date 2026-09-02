package com.nexel.socialai.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public record CompanyResponse(
        @Schema(description = "Unique company identifier", example = "f7ed1c63-9e7d-4113-b440-42d43694fd68")
        UUID id,

        @Schema(description = "Owner user identifier", example = "9a0e9d2e-c960-4ef3-a40d-a1b7cfd3d0d5")
        UUID ownerId,

        @Schema(description = "Company name", example = "Nexel Labs")
        String name,

        @Schema(description = "Business segment", example = "Technology")
        String segment,

        @Schema(description = "Creation timestamp", example = "2026-09-01T22:00:00Z")
        Instant createdAt,

        @Schema(description = "Last update timestamp", example = "2026-09-01T22:05:00Z")
        Instant updatedAt
) {
}
