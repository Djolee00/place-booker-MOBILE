package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlaceLocationDto(
    @NotBlank String address,
    @NotBlank String staticMapImageUrl,
    @NotBlank String lat,
    @NotBlank String lng) {}
