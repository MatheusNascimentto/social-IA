package com.nexel.socialai.user.controller;

import com.nexel.socialai.common.response.ApiResponse;
import com.nexel.socialai.user.dto.UpdateUserRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(Principal principal) {
        UserResponse user = com.nexel.socialai.user.mapper.UserMapper.toResponse(userService.findByEmail(principal.getName()));
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Current user retrieved")
                .data(user)
                .timestamp(java.time.Instant.now())
                .build());
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMe(Principal principal, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse updated = userService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User updated successfully")
                .data(updated)
                .timestamp(java.time.Instant.now())
                .build());
    }
}
