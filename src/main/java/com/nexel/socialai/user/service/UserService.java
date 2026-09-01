package com.nexel.socialai.user.service;

import com.nexel.socialai.auth.dto.AuthResponse;
import com.nexel.socialai.user.dto.LoginRequest;
import com.nexel.socialai.user.dto.RegisterRequest;
import com.nexel.socialai.user.dto.UpdateUserRequest;
import com.nexel.socialai.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(RegisterRequest request);

    AuthResponse authenticate(LoginRequest request);

    User findByEmail(String email);

    com.nexel.socialai.user.dto.UserResponse updateProfile(String email, UpdateUserRequest request);
}
