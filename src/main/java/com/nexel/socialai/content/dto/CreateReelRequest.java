package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateReelRequest(
        @Schema(description = "Company id to use as context") @NotNull UUID companyId,
        @Schema(description = "Prompt describing the reel to create", example = "A 30s reel showing a before/after landscaping job") @NotBlank String prompt,
        @Schema(description = "Optional desired number of script steps") Integer steps
) {}
