package com.varun.workforce_management.auth_module.service;

import com.varun.workforce_management.auth_module.entity.RefreshToken;
import com.varun.workforce_management.auth_module.repository.RefreshTokenRepository;
import com.varun.workforce_management.auth_module.repository.UserRepository;
import com.varun.workforce_management.exception.TokenRefreshException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElse(RefreshToken.builder()
                        .user(user)
                        .build());

        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusSeconds(30)); // 30 seconds as per previous change

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getRefreshToken(),
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public RefreshToken rotateRefreshToken(RefreshToken token) {
        return createRefreshToken(token.getUser().getEmail());
    }

    public java.util.Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public void deleteToken(Long token){
        refreshTokenRepository.deleteById(token);
    }
}
