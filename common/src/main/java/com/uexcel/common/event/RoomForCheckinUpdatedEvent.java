package com.uexcel.common.event;

import com.uexcel.common.RoomStatus;
import lombok.Data;

@Data
public class RoomForCheckinUpdatedEvent {
   private String roomNumber;
   private RoomStatus roomStatus;
}
