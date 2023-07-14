package com.placebooker.dto;

import com.placebooker.domain.PlaceLocation;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PlaceDto(
    Long id,
    String title,
    String description,
    String imageUrl,
    Double price,
    LocalDate availableFrom,
    LocalDate availableTo,
    UserDto user,
    PlaceLocation placeLocation) {}
