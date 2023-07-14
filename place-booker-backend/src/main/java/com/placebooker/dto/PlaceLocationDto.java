package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlaceLocationDto(String address, String staticMapImageUrl) {}
