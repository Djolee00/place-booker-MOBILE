package com.placebooker.dto;

import lombok.Builder;

@Builder
public record UserDto(
    String email, String password, String firstName, String lastName, Integer age) {}
