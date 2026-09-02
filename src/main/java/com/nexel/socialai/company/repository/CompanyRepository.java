package com.nexel.socialai.company.repository;

import com.nexel.socialai.company.entity.Company;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Page<Company> findByOwnerId(UUID ownerId, Pageable pageable);
}
