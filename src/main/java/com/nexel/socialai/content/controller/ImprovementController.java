package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.CreateImprovementRequest;
import com.nexel.socialai.content.dto.ImprovementResponse;
import com.nexel.socialai.content.service.ImprovementService;
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
@RequestMapping("/improvements")
@RequiredArgsConstructor
@Tag(name = "Improvements", description = "Content improvement endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ImprovementController {

    private final ImprovementService improvementService;

    @PostMapping
    @Operation(summary = "Improve existing content and save the result")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Content improved and saved"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ImprovementResponse> improve(Principal principal, @Valid @RequestBody CreateImprovementRequest request) {
        ImprovementResponse response = improvementService.improveAndSave(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
