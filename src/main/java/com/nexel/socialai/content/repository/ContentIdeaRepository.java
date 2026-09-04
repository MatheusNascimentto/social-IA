package com.nexel.socialai.content.repository;

import com.nexel.socialai.content.entity.ContentIdea;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentIdeaRepository extends JpaRepository<ContentIdea, UUID> {
}
