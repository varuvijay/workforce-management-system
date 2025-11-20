package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.entity.RefreshToken;
import com.varun.workforce_management.auth_module.repository.RefreshTokenRepository;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email).orElseThrow())
                .refreshToken(UUID.randomUUID().toString())
                .expiresAt(Instant.now().plusMillis(600000)) // 10 minutes
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(
                    token.getRefreshToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public RefreshToken rotateRefreshToken(RefreshToken token) {
        refreshTokenRepository.delete(token);
        return createRefreshToken(token.getUser().getEmail());
    }

    public java.util.Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }
}
