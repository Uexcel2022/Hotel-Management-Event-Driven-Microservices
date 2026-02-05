package com.uexcel.bookingservice.query;

import lombok.Value;

import java.time.LocalDate;

@Value
public class FindCheckinQuery {
    String roomTypeId;
    LocalDate bookingDate;
}
