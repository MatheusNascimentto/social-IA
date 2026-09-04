package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.CaptionResponse;
import com.nexel.socialai.content.dto.CreateCaptionRequest;
import com.nexel.socialai.content.service.CaptionService;
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
@RequestMapping("/captions")
@RequiredArgsConstructor
@Tag(name = "Captions", description = "Caption generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CaptionController {

    private final CaptionService captionService;

    @PostMapping
    @Operation(summary = "Generate and save a caption for a company")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Caption generated and saved"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CaptionResponse> generateCaption(Principal principal, @Valid @RequestBody CreateCaptionRequest request) {
        CaptionResponse response = captionService.generateAndSave(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
