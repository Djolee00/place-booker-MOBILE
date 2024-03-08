package com.placebooker.dto;

import com.placebooker.domain.Role;
import jakarta.validation.constraints.NotNull;

public record RoleDto(@NotNull Role.RoleCode code) {}
