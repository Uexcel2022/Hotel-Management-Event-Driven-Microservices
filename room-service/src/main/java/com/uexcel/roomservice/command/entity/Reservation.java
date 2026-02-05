package com.uexcel.roomservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    private String reservationId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;
}
