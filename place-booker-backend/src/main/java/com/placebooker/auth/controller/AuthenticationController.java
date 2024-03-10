package com.placebooker.auth.controller;

import com.placebooker.auth.AuthenticationService;
import com.placebooker.auth.model.request.SignInRequest;
import com.placebooker.auth.model.request.SignUpRequest;
import com.placebooker.auth.model.request.TokenRefreshRequest;
import com.placebooker.auth.model.response.JwtAuthenticationResponse;
import com.placebooker.auth.model.response.TokenRefreshResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(
            @Valid @RequestBody TokenRefreshRequest request) {
        String token = authenticationService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(new TokenRefreshResponse(token, request.refreshToken()));
    }
}
