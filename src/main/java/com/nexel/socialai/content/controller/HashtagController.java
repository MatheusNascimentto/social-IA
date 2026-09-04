package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.CreateHashtagRequest;
import com.nexel.socialai.content.dto.HashtagResponse;
import com.nexel.socialai.content.service.HashtagService;
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
@RequestMapping("/hashtags")
@RequiredArgsConstructor
@Tag(name = "Hashtags", description = "Hashtag generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class HashtagController {

    private final HashtagService hashtagService;

    @PostMapping
    @Operation(summary = "Generate and save hashtags for a company")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hashtags generated and saved"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<HashtagResponse> generateHashtags(Principal principal, @Valid @RequestBody CreateHashtagRequest request) {
        HashtagResponse response = hashtagService.generateAndSave(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
