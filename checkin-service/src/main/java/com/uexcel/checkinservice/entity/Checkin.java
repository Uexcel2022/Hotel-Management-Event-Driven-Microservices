package com.uexcel.checkinservice.entity;

import com.uexcel.checkinservice.CheckinStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Checkin {
    @Id
    private String checkinId;
    private String paymentId;
    private String roomNumber;
    private String customerName;
    private String mobileNumber;
    private String nextOfKinMobileNumber;
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;
    private double paid;
    @Column(nullable = true)
    private String reservationId;
    private CheckinStatus checkinStatus;
}
