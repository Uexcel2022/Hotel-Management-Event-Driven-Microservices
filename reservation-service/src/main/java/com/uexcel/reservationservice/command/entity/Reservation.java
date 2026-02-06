package com.uexcel.reservationservice.command.entity;
import com.uexcel.common.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    private String reservationId;
    private String roomTypeId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int bookedQuantity;
    private double price;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}


