package com.nexel.socialai.content.repository;

import com.nexel.socialai.content.entity.ContentCalendar;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentCalendarRepository extends JpaRepository<ContentCalendar, UUID> {
}
