package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateImprovementRequest(
        @Schema(description = "Company id to use as context") @NotNull UUID companyId,
        @Schema(description = "Original content to be improved") @NotBlank String content,
        @Schema(description = "Optional instruction/prompt for improvement", example = "Make it shorter and more friendly") String prompt
) {}
