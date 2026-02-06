package com.uexcel.reservationservice.command.repository;

import com.uexcel.reservationservice.command.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin,String> {
    int countByRoomTypeIdAndCheckinDate(String roomTypeId, LocalDate checkinDate);
}
