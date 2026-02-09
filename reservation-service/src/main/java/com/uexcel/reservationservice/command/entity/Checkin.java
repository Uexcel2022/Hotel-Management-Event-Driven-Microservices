package com.uexcel.reservationservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Checkin {
    @Id
    private String checkinId;
    private String roomNumber;
    private String roomTypeId;
    private String roomTypeName;
    private LocalDate checkinDate;
    private LocalDate checkinTime;
    private String customerName;
    private String phoneNumber;
    private String status;
}
