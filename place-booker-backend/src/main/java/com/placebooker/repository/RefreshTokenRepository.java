package com.placebooker.repository;

import com.placebooker.domain.RefreshToken;
import com.placebooker.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Query("SELECT r FROM RefreshToken r WHERE r.user = :user ORDER BY r.expiryDate DESC LIMIT 1")
    RefreshToken findUserLatestRefreshToken(User user);
}
