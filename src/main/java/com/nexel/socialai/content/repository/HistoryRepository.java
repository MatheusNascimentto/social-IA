package com.nexel.socialai.content.repository;

import com.nexel.socialai.content.entity.HistoryEntry;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntry, UUID> {
    List<HistoryEntry> findByCompanyId(UUID companyId);
    List<HistoryEntry> findByCompanyIdAndContentType(UUID companyId, String contentType);
}
