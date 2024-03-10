package com.placebooker.auth.model.response;

public record TokenRefreshResponse(String accessToken, String refreshToken, String tokenType) {
    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}
