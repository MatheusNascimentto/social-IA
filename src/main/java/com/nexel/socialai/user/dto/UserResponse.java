package com.nexel.socialai.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record UserResponse(
        @Schema(description = "Unique user identifier", example = "9a0e9d2e-c960-4ef3-a40d-a1b7cfd3d0d5")
        UUID id,

        @Schema(description = "User full name", example = "Maria Silva")
        String fullName,

        @Schema(description = "User email address", example = "maria@example.com")
        String email
) {
}
