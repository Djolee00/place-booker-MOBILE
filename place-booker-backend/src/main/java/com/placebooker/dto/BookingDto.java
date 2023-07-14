package com.placebooker.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record BookingDto(
    Long id,
    String title,
    String firstName,
    String lastName,
    Integer guestNumber,
    LocalDate bookedFrom,
    LocalDate bookedTo) {}
