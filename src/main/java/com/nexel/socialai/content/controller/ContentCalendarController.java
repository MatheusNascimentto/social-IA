package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.CreateContentCalendarRequest;
import com.nexel.socialai.content.dto.ContentCalendarResponse;
import com.nexel.socialai.content.service.ContentCalendarService;
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
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Tag(name = "Calendar", description = "Content calendar generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ContentCalendarController {

    private final ContentCalendarService contentCalendarService;

    @PostMapping
    @Operation(summary = "Generate and save a content calendar for a company")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Calendar generated and saved"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ContentCalendarResponse> generateCalendar(Principal principal, @Valid @RequestBody CreateContentCalendarRequest request) {
        ContentCalendarResponse response = contentCalendarService.generateAndSave(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
