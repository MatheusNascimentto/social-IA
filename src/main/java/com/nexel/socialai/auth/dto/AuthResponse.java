package com.nexel.socialai.auth.dto;

import com.nexel.socialai.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse(
        @Schema(description = "JWT token used for authenticated requests", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Token validity period in minutes", example = "60")
        long expiresIn,

        @Schema(description = "Authenticated user data")
        UserResponse user
) {
}
