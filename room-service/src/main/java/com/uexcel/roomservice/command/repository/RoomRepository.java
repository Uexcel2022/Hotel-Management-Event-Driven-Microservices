package com.uexcel.roomservice.command.repository;

import com.uexcel.roomservice.command.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {
    boolean existsByNumber(String number);
}
