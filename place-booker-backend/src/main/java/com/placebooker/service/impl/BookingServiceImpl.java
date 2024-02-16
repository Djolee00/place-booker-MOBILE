package com.placebooker.service.impl;

import com.placebooker.domain.Booking;
import com.placebooker.domain.User;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.BookingRepository;
import com.placebooker.service.BookingService;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Set<Booking> getBookingsByUser(User user) {
        return bookingRepository.findBookingByUser(user);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        "Booking with ID: " + id + " can not be found"));
    }

    @Override
    public void removeBooking(Booking booking) {
        bookingRepository.delete(booking);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        if (!((booking.getBookedFrom().isEqual(booking.getPlace().getAvailableFrom())
                        || booking.getBookedFrom().isAfter(booking.getPlace().getAvailableFrom()))
                && (booking.getBookedTo().isEqual(booking.getPlace().getAvailableTo())
                        || booking.getBookedTo().isBefore(booking.getPlace().getAvailableTo()))))
            throw new IllegalArgumentException(
                    "Booking dates must be in range of that place's available date range");

        if (Objects.equals(booking.getUser().getId(), booking.getPlace().getUser().getId())) {
            throw new IllegalArgumentException("User who made place can't book that place");
        }

        return bookingRepository.save(booking);
    }
}
