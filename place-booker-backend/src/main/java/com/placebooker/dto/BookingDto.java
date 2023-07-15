package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.placebooker.constraint.DateOrder;
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
    String title,
    String firstName,
    String lastName,
    Integer guestNumber,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedFrom,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedTo,
    UserDto user,
    PlaceDto place) {}
