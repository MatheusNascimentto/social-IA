package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.GenerateContentRequest;
import com.nexel.socialai.content.dto.GeneratedContentResponse;
import com.nexel.socialai.content.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
@Tag(name = "Content", description = "AI-powered content generation for company brands")
@SecurityRequirement(name = "bearerAuth")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/generate")
    @Operation(summary = "Generate content using the company context and the configured AI provider")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Content generated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or invalid company"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "AI generation failed")
    })
    public ResponseEntity<GeneratedContentResponse> generate(Principal principal,
                                                            @Valid @RequestBody GenerateContentRequest request) {
        GeneratedContentResponse response = contentService.generate(principal.getName(), request);
        return ResponseEntity.ok(response);
    }
}
