package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingDto(
    Long id,
    String title,
    String firstName,
    String lastName,
    Integer guestNumber,
    LocalDate bookedFrom,
    LocalDate bookedTo) {}
