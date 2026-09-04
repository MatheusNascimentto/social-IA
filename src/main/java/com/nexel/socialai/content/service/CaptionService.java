package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.CaptionResponse;
import com.nexel.socialai.content.dto.CreateCaptionRequest;

public interface CaptionService {

    CaptionResponse generateAndSave(String requesterEmail, CreateCaptionRequest request);
}
