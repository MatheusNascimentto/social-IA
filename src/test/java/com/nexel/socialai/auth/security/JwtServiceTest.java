package com.nexel.socialai.auth.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

    @Test
    void shouldGenerateAndValidateToken() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "change-this-secret-in-production");
        ReflectionTestUtils.setField(jwtService, "expirationMinutes", 60L);

        String token = jwtService.generateToken("user@example.com", UUID.randomUUID());

        assertThat(jwtService.isTokenValid(token)).isTrue();
        assertThat(jwtService.extractEmail(token)).isEqualTo("user@example.com");
    }
}
