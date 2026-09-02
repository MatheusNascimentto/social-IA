package com.nexel.socialai.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Schema(description = "Full name of the user", example = "Maria Silva")
        @NotBlank(message = "Full name is required.")
        String fullName,

        @Schema(description = "Email address used for authentication", example = "maria@example.com")
        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email,

        @Schema(description = "Password used to authenticate the user", example = "P@ssw0rd123")
        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must have at least 8 characters.")
        String password
) {
}
