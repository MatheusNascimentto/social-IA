package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.GenerateContentRequest;
import com.nexel.socialai.content.dto.GeneratedContentResponse;

public interface ContentService {

    GeneratedContentResponse generate(String requesterEmail, GenerateContentRequest request);
}
