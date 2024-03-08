package com.placebooker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record UserPatchRequest(@NotNull @Valid Set<RoleDto> roles) {}
