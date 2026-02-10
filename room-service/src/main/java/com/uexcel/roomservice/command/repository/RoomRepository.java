package com.uexcel.roomservice.command.repository;

import com.uexcel.common.RoomStatus;
import com.uexcel.roomservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {
    boolean existsByRoomNumber(String number);
    List<Room> findByRoomTypeIdAndRoomStatus(String roomTypeId, RoomStatus roomStatus);
    List<Room>findByRoomTypeId(String rooTypeID);
    Room findByRoomNumber(String roomNumber);
}
