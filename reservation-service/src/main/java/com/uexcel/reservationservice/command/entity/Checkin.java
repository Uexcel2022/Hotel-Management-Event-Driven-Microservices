package com.uexcel.reservationservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Checkin {
    @Id
    private String checkinId;
    private String roomNumber;
    private String roomTypeId;
    private String roomTypeName;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String customerName;
    private String phoneNumber;
    private double paid;
}
