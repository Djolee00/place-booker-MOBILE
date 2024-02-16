package com.placebooker.service;

import com.placebooker.domain.Booking;
import com.placebooker.domain.User;
import java.util.Set;

public interface BookingService {

    Set<Booking> getBookingsByUser(User user);

    Booking getBookingById(Long id);

    void removeBooking(Booking booking);

    Booking saveBooking(Booking booking);
}
