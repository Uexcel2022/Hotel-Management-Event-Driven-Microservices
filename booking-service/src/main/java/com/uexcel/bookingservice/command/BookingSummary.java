package com.uexcel.bookingservice.command;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
public class BookingSummary {
    @Id
    private String roomType;
    private int numberOfRoomAvailable;
    private LocalDate date;
}
