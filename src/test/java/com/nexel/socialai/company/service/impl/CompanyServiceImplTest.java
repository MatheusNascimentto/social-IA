package com.nexel.socialai.company.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.company.dto.CompanyResponse;
import com.nexel.socialai.company.dto.CreateCompanyRequest;
import com.nexel.socialai.company.dto.UpdateCompanyRequest;
import com.nexel.socialai.company.entity.Company;
import com.nexel.socialai.company.repository.CompanyRepository;
import com.nexel.socialai.user.entity.User;
import com.nexel.socialai.user.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void shouldCreateCompany() {
        User owner = buildUser("owner@example.com");
        CreateCompanyRequest request = new CreateCompanyRequest("Nexel Labs", "Technology");
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .name("Nexel Labs")
                .segment("Technology")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        CompanyResponse response = companyService.create("owner@example.com", request);

        assertThat(response.name()).isEqualTo("Nexel Labs");
        assertThat(response.ownerId()).isEqualTo(owner.getId());
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void shouldRejectUpdateWhenUserIsNotOwner() {
        User owner = buildUser("owner@example.com");
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .name("Nexel Labs")
                .segment("Technology")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        UpdateCompanyRequest request = new UpdateCompanyRequest("New Name", "Marketing");

        assertThatThrownBy(() -> companyService.update("other@example.com", company.getId(), request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("You are not the owner of this company.");
    }

    @Test
    void shouldListCompaniesForOwner() {
        User owner = buildUser("owner@example.com");
        Company company = Company.builder()
                .id(UUID.randomUUID())
                .owner(owner)
                .name("Nexel Labs")
                .segment("Technology")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        PageRequest pageable = PageRequest.of(0, 10);
        Page<Company> companyPage = new PageImpl<>(List.of(company), pageable, 1);

        when(userRepository.findByEmail("owner@example.com")).thenReturn(Optional.of(owner));
        when(companyRepository.findByOwnerId(owner.getId(), pageable)).thenReturn(companyPage);

        Page<CompanyResponse> result = companyService.listByOwner("owner@example.com", pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().name()).isEqualTo("Nexel Labs");
    }

    private User buildUser(String email) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPasswordHash("encoded-password");
        user.setFullName("Test User");
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return user;
    }
}
