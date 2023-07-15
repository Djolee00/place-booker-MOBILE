package com.placebooker.auth.model.request;

import lombok.Builder;

@Builder
public record SignInRequest(String email, String password) {}
