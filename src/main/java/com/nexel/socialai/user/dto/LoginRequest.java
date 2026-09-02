package com.nexel.socialai.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(description = "User email to authenticate", example = "maria@example.com")
        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be valid.")
        String email,

        @Schema(description = "User password", example = "P@ssw0rd123")
        @NotBlank(message = "Password is required.")
        String password
) {
}
