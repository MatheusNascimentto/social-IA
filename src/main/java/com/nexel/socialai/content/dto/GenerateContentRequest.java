package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GenerateContentRequest(
        @Schema(description = "Company identifier to use as context for content generation", example = "d2d5f4c9-1d7b-4a0c-9d95-fcdbef6d8328")
        @NotNull(message = "Company id is required.")
        UUID companyId,

        @Schema(description = "Content platform", example = "INSTAGRAM")
        @NotBlank(message = "Platform is required.")
        String platform,

        @Schema(description = "Content format or objective", example = "CAPTION")
        @NotBlank(message = "Content type is required.")
        String contentType,

        @Schema(description = "Specific task or prompt to send to the AI", example = "Write a catchy post for a landscaping company promoting weekend offers.")
        @NotBlank(message = "Prompt is required.")
        String prompt,

        @Schema(description = "Optional communication tone", example = "professional and direct")
        String tone
) {
}
