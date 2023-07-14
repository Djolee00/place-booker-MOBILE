package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlaceDto(
    Long id,
    @NotBlank String title,
    String description,
    String imageUrl,
    @NotNull @Positive Double price,
    @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate availableFrom,
    @NotNull @FutureOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableTo,
    @NotNull UserDto user,
    @NotNull PlaceLocationDto placeLocation) {}
