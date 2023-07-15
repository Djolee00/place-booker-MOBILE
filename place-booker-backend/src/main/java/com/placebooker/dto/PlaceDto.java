package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.placebooker.constraint.DateOrder;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@DateOrder(
    dateFromField = "availableFrom",
    dateToField = "availableTo",
    message = "Available from date must be less or equal to available to date")
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
