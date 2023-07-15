package com.placebooker.controller;

import com.placebooker.domain.Booking;
import com.placebooker.domain.User;
import com.placebooker.dto.BookingDto;
import com.placebooker.mapper.BookingMapper;
import com.placebooker.service.BookingService;
import com.placebooker.service.UserService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final UserService userService;
  private final BookingService bookingService;

  public BookingController(UserService userService, BookingService bookingService) {
    this.userService = userService;
    this.bookingService = bookingService;
  }

  @GetMapping
  public ResponseEntity<Set<BookingDto>> getBookingsByUser(@RequestParam Long userId) {
    User user = userService.getUserById(userId);
    Set<Booking> bookings = bookingService.getBookingsByUser(user);
    return ResponseEntity.ok(
        bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet()));
  }

  @DeleteMapping("/{id}")
  public void deleteBooking(@PathVariable Long id) {
    Booking booking = bookingService.getBookingById(id);
    bookingService.removeBooking(booking);
  }
}
