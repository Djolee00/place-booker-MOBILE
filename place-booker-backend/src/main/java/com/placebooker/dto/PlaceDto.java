package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlaceDto(
    Long id,
    String title,
    String description,
    String imageUrl,
    Double price,
    LocalDate availableFrom,
    LocalDate availableTo,
    UserDto user,
    PlaceLocationDto placeLocation) {}
