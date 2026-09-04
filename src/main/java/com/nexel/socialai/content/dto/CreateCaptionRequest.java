package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateCaptionRequest(
        @Schema(description = "Company id to use as context", example = "d2d5f4c9-1d7b-4a0c-9d95-fcdbef6d8328")
        @NotNull UUID companyId,

        @Schema(description = "Platform", example = "INSTAGRAM")
        @NotBlank String platform,

        @Schema(description = "Content type", example = "CAPTION")
        @NotBlank String contentType,

        @Schema(description = "Prompt for the AI to refine/generate the caption", example = "Create a catchy weekend offer caption")
        @NotBlank String prompt,

        @Schema(description = "Optional tone", example = "professional and direct")
        String tone
) {
}
