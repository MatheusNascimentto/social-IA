package com.nexel.socialai.auth.controller;

import com.nexel.socialai.auth.dto.AuthResponse;
import com.nexel.socialai.common.response.ApiResponse;
import com.nexel.socialai.user.dto.LoginRequest;
import com.nexel.socialai.user.dto.RegisterRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.entity.User;
import com.nexel.socialai.user.mapper.UserMapper;
import com.nexel.socialai.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        UserResponse response = UserMapper.toResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User registered successfully.")
                        .data(response)
                        .timestamp(java.time.Instant.now())
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.authenticate(request);

        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Authentication successful.")
                .data(response)
                .timestamp(java.time.Instant.now())
                .build());
    }
}
