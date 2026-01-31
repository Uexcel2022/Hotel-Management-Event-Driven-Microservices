package com.uexcel.bookingservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Checkin {
    @Id
    private String checkinId;
    private String roomNumber;
    private String checkinDate;
    private String checkinTime;
    private String customerName;
    private String phoneNumber;
    private String status;
}
