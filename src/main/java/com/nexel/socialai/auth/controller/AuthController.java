package com.nexel.socialai.auth.controller;

import com.nexel.socialai.auth.dto.AuthResponse;
import com.nexel.socialai.user.dto.LoginRequest;
import com.nexel.socialai.user.dto.RegisterRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.entity.User;
import com.nexel.socialai.user.mapper.UserMapper;
import com.nexel.socialai.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Authentication and user registration endpoints")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<com.nexel.socialai.common.response.ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        UserResponse response = UserMapper.toResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(com.nexel.socialai.common.response.ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("User registered successfully.")
                        .data(response)
                        .timestamp(java.time.Instant.now())
                        .build());
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and return a JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or validation error")
    })
    public ResponseEntity<com.nexel.socialai.common.response.ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.authenticate(request);

        return ResponseEntity.ok(com.nexel.socialai.common.response.ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Authentication successful.")
                .data(response)
                .timestamp(java.time.Instant.now())
                .build());
    }
}
