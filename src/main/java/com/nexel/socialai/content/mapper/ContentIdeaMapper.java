package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.ContentIdeaResponse;
import com.nexel.socialai.content.entity.ContentIdea;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class ContentIdeaMapper {

    private ContentIdeaMapper() { }

    public static ContentIdeaResponse toResponse(ContentIdea entity) {
        List<String> ideas = Arrays.stream(entity.getIdeas().split("\\r?\\n"))
                .map(String::trim)
                .map(s -> s.replaceAll("^\\?\d+\.\\s*", "")) // remove leading numbering like "1. "
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return new ContentIdeaResponse(entity.getId(), entity.getCompany().getId(), ideas, entity.getCreatedAt());
    }
}
