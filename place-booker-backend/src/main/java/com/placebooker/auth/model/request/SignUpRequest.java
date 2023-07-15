package com.placebooker.auth.model.request;

import lombok.Builder;

@Builder
public record SignUpRequest(
    String firstName, String lastName, String email, String password, Integer age) {}
