package com.nexel.socialai.user.controller;

import com.nexel.socialai.user.dto.UpdateUserRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Users", description = "Authenticated user profile endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UsersController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get the authenticated user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User profile retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<com.nexel.socialai.common.response.ApiResponse<UserResponse>> me(Principal principal) {
        UserResponse user = com.nexel.socialai.user.mapper.UserMapper.toResponse(userService.findByEmail(principal.getName()));
        return ResponseEntity.ok(com.nexel.socialai.common.response.ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Current user retrieved")
                .data(user)
                .timestamp(java.time.Instant.now())
                .build());
    }

    @PutMapping("/me")
    @Operation(summary = "Update the authenticated user profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<com.nexel.socialai.common.response.ApiResponse<UserResponse>> updateMe(Principal principal, @Valid @RequestBody UpdateUserRequest request) {
        UserResponse updated = userService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok(com.nexel.socialai.common.response.ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User updated successfully")
                .data(updated)
                .timestamp(java.time.Instant.now())
                .build());
    }
}
