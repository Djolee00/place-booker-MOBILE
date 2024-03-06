package com.placebooker.auth.model.response;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record JwtAuthenticationResponse(
        String email, String token, OffsetDateTime expirationTime) {}
