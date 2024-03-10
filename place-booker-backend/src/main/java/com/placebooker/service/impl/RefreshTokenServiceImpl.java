package com.placebooker.service.impl;

import com.placebooker.domain.RefreshToken;
import com.placebooker.domain.User;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.exception.custom.TokenRefreshException;
import com.placebooker.repository.RefreshTokenRepository;
import com.placebooker.service.RefreshTokenService;
import com.placebooker.service.UserService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Value("${refresh.token.duration.ms}")
    private Long refreshTokenDurationMs;

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(
                        () -> new NotFoundException("Refresh token " + token + " doesn't exist"));
    }

    @Override
    @Transactional
    public RefreshToken getOrCreateRefreshToken(User user) {
        RefreshToken userLatestRefreshToken = findLatestRefreshTokenByUser(user);
        if (userLatestRefreshToken != null
                && userLatestRefreshToken.getExpiryDate().compareTo(Instant.now()) > 0) {
            return userLatestRefreshToken;
        }

        RefreshToken refreshToken =
                new RefreshToken()
                        .setUser(userService.getUserById(user.getId()))
                        .setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                        .setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    @Transactional
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(
                    "Refresh token was expired. Please make a new signin request");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findLatestRefreshTokenByUser(User user) {
        return refreshTokenRepository.findUserLatestRefreshToken(user);
    }
}
