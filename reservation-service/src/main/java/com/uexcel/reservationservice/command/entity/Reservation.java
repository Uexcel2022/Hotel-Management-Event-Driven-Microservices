package com.uexcel.reservationservice.command.entity;
import com.uexcel.common.ReservationStatus;
import com.uexcel.common.PaymentStatus;
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
    private String roomTypeName;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookedDate;
    private double price;
    private double total;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

}


