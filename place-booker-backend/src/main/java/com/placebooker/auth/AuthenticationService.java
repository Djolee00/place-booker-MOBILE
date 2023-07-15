package com.placebooker.auth;

import com.placebooker.auth.model.request.SignInRequest;
import com.placebooker.auth.model.request.SignUpRequest;
import com.placebooker.auth.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {

  JwtAuthenticationResponse signUp(SignUpRequest request);

  JwtAuthenticationResponse signIn(SignInRequest request);
}
