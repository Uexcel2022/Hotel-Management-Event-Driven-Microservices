package com.uexcel.roomservice.entity;

import com.uexcel.common.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Room {
    @Id
    private String roomNumber;
    private String roomTypeId;
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
}
