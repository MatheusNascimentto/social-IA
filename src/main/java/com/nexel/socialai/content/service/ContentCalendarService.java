package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.CreateContentCalendarRequest;
import com.nexel.socialai.content.dto.ContentCalendarResponse;

public interface ContentCalendarService {

    ContentCalendarResponse generateAndSave(String requesterEmail, CreateContentCalendarRequest request);
}
