package com.uexcel.reservationservice.command.repository;

import com.uexcel.reservationservice.command.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,String> {
    Reservation findByReservationId(String reservationId);
}
