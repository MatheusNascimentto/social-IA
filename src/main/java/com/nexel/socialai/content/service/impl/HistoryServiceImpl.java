package com.nexel.socialai.content.service.impl;

import com.nexel.socialai.content.dto.HistoryResponse;
import com.nexel.socialai.content.mapper.HistoryMapper;
import com.nexel.socialai.content.repository.HistoryRepository;
import com.nexel.socialai.content.service.HistoryService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public List<HistoryResponse> listByCompany(UUID companyId) {
        return historyRepository.findByCompanyId(companyId).stream()
                .map(HistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoryResponse> listByCompanyAndType(UUID companyId, String type) {
        return historyRepository.findByCompanyIdAndContentType(companyId, type).stream()
                .map(HistoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
