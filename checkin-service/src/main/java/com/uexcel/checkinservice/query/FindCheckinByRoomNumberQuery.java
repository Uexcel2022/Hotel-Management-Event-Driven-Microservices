package com.uexcel.checkinservice.query;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FindCheckinByRoomNumberQuery {
    String roomNumber;
}
