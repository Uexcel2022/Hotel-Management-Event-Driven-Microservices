package com.uexcel.reservationservice.command;

import com.uexcel.common.BookingStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PublishReservationConfirmedEvent {
    private String reservationId;
    private String roomTypeName;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int bookedQuantity;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
