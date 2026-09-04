package com.nexel.socialai.content.mapper;

import com.nexel.socialai.content.dto.ContentCalendarResponse;
import com.nexel.socialai.content.entity.ContentCalendar;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ContentCalendarMapper {

    private ContentCalendarMapper() { }

    public static ContentCalendarResponse toResponse(ContentCalendar entity) {
        List<String> entries = Arrays.stream(entity.getEntries().split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return new ContentCalendarResponse(entity.getId(), entity.getCompany().getId(), entries, entity.getCreatedAt());
    }
}
