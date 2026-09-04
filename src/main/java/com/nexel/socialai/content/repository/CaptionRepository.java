package com.nexel.socialai.content.repository;

import com.nexel.socialai.content.entity.Caption;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptionRepository extends JpaRepository<Caption, UUID> {
}
