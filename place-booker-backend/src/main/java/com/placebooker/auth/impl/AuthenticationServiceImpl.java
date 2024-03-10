package com.placebooker.auth.impl;

import com.placebooker.auth.AuthenticationService;
import com.placebooker.auth.JwtService;
import com.placebooker.auth.model.request.SignInRequest;
import com.placebooker.auth.model.request.SignUpRequest;
import com.placebooker.auth.model.response.JwtAuthenticationResponse;
import com.placebooker.domain.RefreshToken;
import com.placebooker.domain.Role;
import com.placebooker.domain.User;
import com.placebooker.repository.UserRepository;
import com.placebooker.service.RefreshTokenService;
import com.placebooker.service.RoleService;
import java.time.ZoneOffset;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user =
                User.builder()
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .age(request.age())
                        .roles(Set.of(roleService.getRoleByCode(Role.RoleCode.USER_BASIC)))
                        .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.getOrCreateRefreshToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId())
                .token(jwt)
                .expirationTime(
                        jwtService
                                .extractExpiration(jwt)
                                .toInstant()
                                .atOffset(ZoneOffset.ofHours(2)))
                .email(user.getEmail())
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user =
                userRepository
                        .findByEmail(request.email())
                        .orElseThrow(
                                () -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.getOrCreateRefreshToken(user);
        return JwtAuthenticationResponse.builder()
                .id(user.getId())
                .token(jwt)
                .expirationTime(
                        jwtService
                                .extractExpiration(jwt)
                                .toInstant()
                                .atOffset(ZoneOffset.ofHours(2)))
                .email(user.getEmail())
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    @Transactional
    public String refreshToken(String refreshToken) {
        RefreshToken generatedRefreshToken = refreshTokenService.findByToken(refreshToken);
        refreshTokenService.verifyExpiration(generatedRefreshToken);
        return jwtService.generateToken(generatedRefreshToken.getUser());
    }
}
