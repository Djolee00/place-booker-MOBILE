package com.placebooker.dto;

import lombok.Builder;

@Builder
public record PlaceLocationDto(String address, String staticMapImageUrl) {}
