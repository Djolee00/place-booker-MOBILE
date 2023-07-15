package com.placebooker.mapper;

import com.placebooker.domain.Booking;
import com.placebooker.dto.BookingDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {

  public static Booking toEntity(BookingDto bookingDto) {
    return Booking.builder()
        .id(bookingDto.id())
        .firstName(bookingDto.firstName())
        .lastName(bookingDto.lastName())
        .bookedFrom(bookingDto.bookedFrom())
        .bookedTo(bookingDto.bookedTo())
        .guestNumber(bookingDto.guestNumber())
        .build();
  }

  public static BookingDto toDto(Booking booking) {
    return BookingDto.builder()
        .id(booking.getId())
        .title(booking.getTitle())
        .bookedFrom(booking.getBookedFrom())
        .bookedTo(booking.getBookedTo())
        .guestNumber(booking.getGuestNumber())
        .firstName(booking.getFirstName())
        .lastName(booking.getLastName())
        .place(booking.getPlace() != null ? PlaceMapper.toDto(booking.getPlace()) : null)
        .user(booking.getUser() != null ? UserMapper.toDto(booking.getUser()) : null)
        .build();
  }
}
