package com.uexcel.bookingservice.command;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Booking {
    @Id
    private String id;
    private String type;
    private LocalDate date;
    private int numberOfRoom;
    private LocalTime numberOfDays;
}
