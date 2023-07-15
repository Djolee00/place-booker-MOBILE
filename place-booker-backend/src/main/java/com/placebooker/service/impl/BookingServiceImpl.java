package com.placebooker.service.impl;

import com.placebooker.domain.Booking;
import com.placebooker.domain.User;
import com.placebooker.exception.custom.NotFoundException;
import com.placebooker.repository.BookingRepository;
import com.placebooker.service.BookingService;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;

  public BookingServiceImpl(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  @Override
  public Set<Booking> getBookingsByUser(User user) {
    return bookingRepository.findBookingByUser(user);
  }

  @Override
  public Booking getBookingById(Long id) {
    return bookingRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Booking with ID: " + id + " can not be found"));
  }

  @Override
  public void removeBooking(Booking booking) {
    bookingRepository.delete(booking);
  }

  @Override
  public Booking saveBooking(Booking booking) {
    return bookingRepository.save(booking);
  }
}
