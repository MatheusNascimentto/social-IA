package com.nexel.socialai.user.service.impl;

import com.nexel.socialai.auth.dto.AuthResponse;
import com.nexel.socialai.auth.security.JwtService;
import com.nexel.socialai.common.exception.BusinessException;
import com.nexel.socialai.user.dto.LoginRequest;
import com.nexel.socialai.user.dto.RegisterRequest;
import com.nexel.socialai.user.dto.UserResponse;
import com.nexel.socialai.user.entity.User;
import com.nexel.socialai.user.mapper.UserMapper;
import com.nexel.socialai.user.repository.UserRepository;
import com.nexel.socialai.user.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.jwt.expiration-minutes}")
    private long expirationMinutes;

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        String normalizedEmail = request.email().trim();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new BusinessException("A user with this email already exists.");
        }

        User user = UserMapper.toEntity(request, passwordEncoder.encode(request.password()));
        user.setEmail(normalizedEmail);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse authenticate(LoginRequest request) {
        User user = findByEmail(request.email().trim());

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getId());
        return new AuthResponse(token, expirationMinutes, new UserResponse(user.getId(), user.getFullName(), user.getEmail()));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email.trim())
                .orElseThrow(() -> new BusinessException("Invalid email or password."));
    }

    @Override
    @Transactional
    public com.nexel.socialai.user.dto.UserResponse updateProfile(String email, com.nexel.socialai.user.dto.UpdateUserRequest request) {
        User user = findByEmail(email.trim());
        user.setFullName(request.fullName());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        User saved = userRepository.save(user);
        return new com.nexel.socialai.user.dto.UserResponse(saved.getId(), saved.getFullName(), saved.getEmail());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByEmail(username);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
