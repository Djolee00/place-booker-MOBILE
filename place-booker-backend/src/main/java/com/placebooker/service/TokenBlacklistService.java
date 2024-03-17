package com.placebooker.service;

public interface TokenBlacklistService {

    void blacklistToken(String token, long expiration);

    boolean isTokenBlacklisted(String token);
}
