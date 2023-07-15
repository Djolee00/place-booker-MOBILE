package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.placebooker.constraint.DateOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@DateOrder(
    dateFromField = "bookedFrom",
    dateToField = "bookedTo",
    message = "Booked from date must be less or equal to booked to date")
public record BookingDto(
    Long id,
    @NotBlank String title,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotNull Integer guestNumber,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedFrom,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedTo,
    @NotNull UserDto user,
    @NotNull PlaceDto place) {}
