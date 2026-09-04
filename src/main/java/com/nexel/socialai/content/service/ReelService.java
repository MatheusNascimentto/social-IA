package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.CreateReelRequest;
import com.nexel.socialai.content.dto.ReelResponse;

public interface ReelService {

    ReelResponse generateAndSave(String requesterEmail, CreateReelRequest request);
}
