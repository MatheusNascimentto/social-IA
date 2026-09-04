package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.CreateContentIdeaRequest;
import com.nexel.socialai.content.dto.ContentIdeaResponse;

public interface ContentIdeaService {

    ContentIdeaResponse generateAndSave(String requesterEmail, CreateContentIdeaRequest request);
}
