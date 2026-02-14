package com.uexcel.roomservice.command.repository;

import com.uexcel.common.RoomStatus;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomInventoryForDateRepository extends JpaRepository<RoomInventoryForDate, String> {
    RoomInventoryForDate findByRoomInventoryForDateId(String roomInventoryForDateId);
    List<RoomInventoryForDate> findByRoomTypeId(String roomTypeId);

    RoomInventoryForDate
    findByRoomTypeNameAndAvailabilityDate(String roomTypeName, LocalDate availabilityDate);
}

