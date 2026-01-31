package com.uexcel.bookingservice.command.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Booking {
    @Id
    private String BookingId;
    private  String roomTypeId;
    private LocalDate date;
    private int numberOfRoom;
    private LocalTime numberOfDays;

}
