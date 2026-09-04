package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.CreateHashtagRequest;
import com.nexel.socialai.content.dto.HashtagResponse;

public interface HashtagService {

    HashtagResponse generateAndSave(String requesterEmail, CreateHashtagRequest request);
}
