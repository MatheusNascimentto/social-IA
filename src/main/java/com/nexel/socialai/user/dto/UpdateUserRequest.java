package com.nexel.socialai.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Schema(description = "Updated full name of the user", example = "Maria Silva")
        @NotBlank(message = "Full name is required.")
        String fullName,

        @Schema(description = "Optional new password (minimum 8 characters)", example = "NovaSenha123")
        @Size(min = 8, message = "Password must have at least 8 characters.")
        String password
) {
}
