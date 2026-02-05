package com.uexcel.roomservice.command.repository;

import com.uexcel.roomservice.command.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Reservation findByRoomTypeIdAndBookingDate(String roomTypeId, LocalDate bookingDate);
}
