package com.nexel.socialai.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UpdateCompanyRequest(
        @Schema(description = "Updated company name", example = "Nexel Labs")
        @Size(max = 255)
        String name,

        @Schema(description = "Updated business segment", example = "Marketing")
        @Size(max = 255)
        String segment
) {
}
