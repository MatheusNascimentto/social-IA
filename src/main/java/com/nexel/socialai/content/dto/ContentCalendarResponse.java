package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ContentCalendarResponse(
        @Schema(description = "Calendar id") UUID id,
        @Schema(description = "Company id") UUID companyId,
        @Schema(description = "List of scheduled entries in the format 'YYYY-MM-DD: idea'") List<String> entries,
        @Schema(description = "Creation timestamp") Instant createdAt
) {}
