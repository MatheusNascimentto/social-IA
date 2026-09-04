package com.nexel.socialai.content.service;

import com.nexel.socialai.content.dto.HistoryResponse;
import java.util.List;
import java.util.UUID;

public interface HistoryService {

    List<HistoryResponse> listByCompany(UUID companyId);
    List<HistoryResponse> listByCompanyAndType(UUID companyId, String type);
}
