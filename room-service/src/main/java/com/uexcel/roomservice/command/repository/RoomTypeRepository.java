package com.uexcel.roomservice.command.repository;


import com.uexcel.roomservice.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {
    Boolean existsByRoomTypeId(String roomTypeId);
    Boolean existsByRoomTypeName(String roomTypeName);
    RoomType findByRoomTypeId(String roomTypeId);
}
