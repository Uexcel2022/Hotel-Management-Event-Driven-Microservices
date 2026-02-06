package com.uexcel.reservationservice.command;


import com.uexcel.common.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

@Data
@Builder
public class CancelReservationCommand {
    @TargetAggregateIdentifier
    private String reservationId;
    private String roomInventoryForDateId;
    private LocalDate bookingDate;
    private String customerName;
    private String mobileNumber;
    private int bookedQuantity;
    private String roomTypeId;
    private String reason;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}
