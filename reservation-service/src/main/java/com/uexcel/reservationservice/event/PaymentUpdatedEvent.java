package com.uexcel.reservationservice.event;
import com.uexcel.common.event.PaymentStatus;
import lombok.Data;

@Data
public class PaymentUpdatedEvent {
    private String reservationId;
    private  PaymentStatus paymentStatus;
}
