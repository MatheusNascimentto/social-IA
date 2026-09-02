package com.nexel.socialai.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCompanyRequest(
        @Schema(description = "Company name", example = "Nexel Labs")
        @NotBlank
        @Size(max = 255)
        String name,

        @Schema(description = "Business segment or industry", example = "Technology")
        @Size(max = 255)
        String segment
) {
}
