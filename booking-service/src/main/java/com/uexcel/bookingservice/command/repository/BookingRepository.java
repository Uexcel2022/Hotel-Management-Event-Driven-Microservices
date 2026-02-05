package com.uexcel.bookingservice.command.repository;

import com.uexcel.bookingservice.command.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
    Booking findByBookingId(String bookingId);
}
