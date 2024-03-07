package com.placebooker.controller;

import com.placebooker.domain.Booking;
import com.placebooker.domain.Place;
import com.placebooker.domain.Role;
import com.placebooker.domain.User;
import com.placebooker.dto.BookingDto;
import com.placebooker.mapper.BookingMapper;
import com.placebooker.service.BookingService;
import com.placebooker.service.PlaceService;
import com.placebooker.service.UserService;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final UserService userService;
    private final BookingService bookingService;
    private final PlaceService placeService;

    public BookingController(
            UserService userService, BookingService bookingService, PlaceService placeService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.placeService = placeService;
    }

    @GetMapping
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public ResponseEntity<Set<BookingDto>> getBookingsByUser(@RequestParam Long userId) {
        User user = userService.getUserById(userId);
        Set<Booking> bookings = bookingService.getBookingsByUser(user);
        return ResponseEntity.ok(
                bookings.stream().map(BookingMapper::toDto).collect(Collectors.toSet()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public void deleteBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        bookingService.removeBooking(booking);
    }

    @PostMapping
    @PreAuthorize("hasRole(\"" + Role.RoleCode.Code.USER_BASIC + "\")")
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        User user = userService.getUserById(bookingDto.user().id());
        Place place = placeService.getPlaceById(bookingDto.place().id());

        Booking booking = BookingMapper.toEntity(bookingDto);
        booking.setUser(user);
        booking.setPlace(place);

        booking = bookingService.saveBooking(booking);
        return new ResponseEntity<>(BookingMapper.toDto(booking), HttpStatus.OK);
    }
}
