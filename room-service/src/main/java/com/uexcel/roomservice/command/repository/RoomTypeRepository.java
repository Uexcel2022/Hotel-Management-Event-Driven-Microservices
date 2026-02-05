package com.uexcel.roomservice.command.repository;


import com.uexcel.roomservice.command.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {
    Boolean existsByRoomTypeId(String roomTypeId);
    Boolean existsByRoomTypeName(String roomTypeName);
    RoomType findByRoomTypeId(String roomTypeId);
}
