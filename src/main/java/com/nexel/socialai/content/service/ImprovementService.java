package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.CreateImprovementRequest;
import com.nexel.socialai.content.dto.ImprovementResponse;

public interface ImprovementService {

    ImprovementResponse improveAndSave(String requesterEmail, CreateImprovementRequest request);
}
