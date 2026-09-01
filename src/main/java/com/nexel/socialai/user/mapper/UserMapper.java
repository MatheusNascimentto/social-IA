package com.nexel.socialai.user.mapper;

import com.nexel.socialai.user.dto.RegisterRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request, String encodedPassword) {
        return User.builder()
                .fullName(request.fullName())
                .email(request.email().trim())
                .passwordHash(encodedPassword)
                .build();
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }
}
