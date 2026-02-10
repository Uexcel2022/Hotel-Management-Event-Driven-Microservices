package com.uexcel.roomservice.command.repository;

import com.uexcel.roomservice.entity.RoomInventoryForDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomInventoryForDateRepository extends JpaRepository<RoomInventoryForDate, String> {
    RoomInventoryForDate findByRoomInventoryForDateId(String roomInventoryForDateId);
    List<RoomInventoryForDate> findByRoomTypeId(String roomTypeId);
}
