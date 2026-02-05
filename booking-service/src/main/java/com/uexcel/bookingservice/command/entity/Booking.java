package com.uexcel.bookingservice.command.entity;
import com.uexcel.common.BookingStatus;
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
    private String bookingId;
    private String roomTypeId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int numberOfRoom;
    private int numberOfDays;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}


