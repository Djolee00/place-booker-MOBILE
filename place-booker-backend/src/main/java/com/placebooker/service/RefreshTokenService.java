package com.placebooker.service;

import com.placebooker.domain.RefreshToken;
import com.placebooker.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RefreshTokenService {

    RefreshToken findByToken(@NotBlank String token);

    RefreshToken getOrCreateRefreshToken(@NotNull User user);

    void verifyExpiration(@Valid RefreshToken refreshToken);

    RefreshToken findLatestRefreshTokenByUser(@NotNull User user);
}
