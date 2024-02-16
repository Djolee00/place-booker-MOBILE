package com.placebooker.repository;

import com.placebooker.domain.Booking;
import com.placebooker.domain.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Set<Booking> findBookingByUser(User user);
}
