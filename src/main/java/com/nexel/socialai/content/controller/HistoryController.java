package com.nexel.socialai.content.controller;

import com.nexel.socialai.content.dto.HistoryResponse;
import com.nexel.socialai.content.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Tag(name = "History", description = "Generated content history")
@SecurityRequirement(name = "bearerAuth")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @Operation(summary = "List generated content history for a company")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List returned"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<HistoryResponse>> list(@RequestParam UUID companyId, @RequestParam(required = false) String type) {
        List<HistoryResponse> result = (type == null || type.isBlank())
                ? historyService.listByCompany(companyId)
                : historyService.listByCompanyAndType(companyId, type);
        return ResponseEntity.ok(result);
    }
}
