package com.nexel.socialai.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record CreateContentCalendarRequest(
        @Schema(description = "Company id to use as context") @NotNull UUID companyId,
        @Schema(description = "Prompt describing the content theme for the calendar", example = "September posts focused on Java performance") @NotBlank String prompt,
        @Schema(description = "Start date for the calendar (ISO), default is today") LocalDate startDate,
        @Schema(description = "Number of days to generate, default 14") Integer days
) {}
