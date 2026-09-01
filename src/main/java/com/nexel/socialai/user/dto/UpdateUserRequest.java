package com.nexel.socialai.user.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "Full name is required.")
        String fullName,

        @Size(min = 8, message = "Password must have at least 8 characters.")
        String password
) {
}
