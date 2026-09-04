package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateHashtagRequest(
        @Schema(description = "Company id to use as context", example = "d2d5f4c9-1d7b-4a0c-9d95-fcdbef6d8328")
        @NotNull UUID companyId,

        @Schema(description = "Prompt describing the content to generate hashtags for", example = "A carousel about Java performance tips")
        @NotBlank String prompt
) {
}
