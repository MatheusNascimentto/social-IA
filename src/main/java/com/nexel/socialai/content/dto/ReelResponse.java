package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReelResponse(
        @Schema(description = "Reel script id") UUID id,
        @Schema(description = "Company id") UUID companyId,
        @Schema(description = "Ordered script steps") List<String> script,
        @Schema(description = "Creation timestamp") Instant createdAt
) {}
