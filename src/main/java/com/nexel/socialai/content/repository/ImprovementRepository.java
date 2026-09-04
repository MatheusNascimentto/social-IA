package com.nexel.socialai.content.repository;

import com.nexel.socialai.content.entity.Improvement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImprovementRepository extends JpaRepository<Improvement, UUID> {
}
