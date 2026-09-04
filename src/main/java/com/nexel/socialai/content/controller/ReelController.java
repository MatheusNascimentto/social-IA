package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.CreateReelRequest;
import com.nexel.socialai.content.dto.ReelResponse;
import com.nexel.socialai.content.service.ReelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reels")
@RequiredArgsConstructor
@Tag(name = "Reels", description = "Reel script generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ReelController {

    private final ReelService reelService;

    @PostMapping
    @Operation(summary = "Generate and save a Reel script for a company")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reel script generated and saved"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ReelResponse> generateReel(Principal principal, @Valid @RequestBody CreateReelRequest request) {
        ReelResponse response = reelService.generateAndSave(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
