package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.HashtagResponse;
import com.nexel.socialai.content.entity.Hashtag;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class HashtagMapper {

    private HashtagMapper() { }

    public static HashtagResponse toResponse(Hashtag entity) {
        List<String> tags = Arrays.stream(entity.getTags().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return new HashtagResponse(entity.getId(), entity.getCompany().getId(), tags, entity.getCreatedAt());
    }
}
