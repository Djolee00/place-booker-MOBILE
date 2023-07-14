package com.placebooker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookingDto(
    Long id,
    String title,
    String firstName,
    String lastName,
    Integer guestNumber,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedFrom,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookedTo) {}
