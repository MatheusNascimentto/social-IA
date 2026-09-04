package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.CreateContentIdeaRequest;
import com.nexel.socialai.content.dto.ContentIdeaResponse;
import com.nexel.socialai.content.service.ContentIdeaService;
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
@RequestMapping("/ideas")
@RequiredArgsConstructor
@Tag(name = "Ideas", description = "Content ideas generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ContentIdeaController {

    private final ContentIdeaService contentIdeaService;

    @PostMapping
    @Operation(summary = "Generate and save content ideas for a company")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ideas generated and saved"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ContentIdeaResponse> generateIdeas(Principal principal, @Valid @RequestBody CreateContentIdeaRequest request) {
        ContentIdeaResponse response = contentIdeaService.generateAndSave(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
