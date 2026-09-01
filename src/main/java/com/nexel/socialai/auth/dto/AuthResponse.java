package com.nexel.socialai.auth.dto;

import com.nexel.socialai.user.dto.UserResponse;

public record AuthResponse(
        String token,
        long expiresIn,
        UserResponse user
) {
}
