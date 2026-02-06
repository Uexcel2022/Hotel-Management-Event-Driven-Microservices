package com.uexcel.roomservice.command.repository;

import com.uexcel.roomservice.command.entity.RoomInventoryForDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInventoryForDateRepository extends JpaRepository<RoomInventoryForDate, String> {
    RoomInventoryForDate findByRoomInventoryForDateId(String roomInventoryForDateId);
}
