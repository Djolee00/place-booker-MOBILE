package com.placebooker.service.impl;

import com.placebooker.domain.Booking;
import com.placebooker.domain.User;
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
}
