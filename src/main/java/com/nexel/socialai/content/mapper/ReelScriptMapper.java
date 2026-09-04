package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.ReelResponse;
import com.nexel.socialai.content.entity.ReelScript;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ReelScriptMapper {

    private ReelScriptMapper() { }

    public static ReelResponse toResponse(ReelScript entity) {
        List<String> steps = Arrays.stream(entity.getScript().split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return new ReelResponse(entity.getId(), entity.getCompany().getId(), steps, entity.getCreatedAt());
    }
}
