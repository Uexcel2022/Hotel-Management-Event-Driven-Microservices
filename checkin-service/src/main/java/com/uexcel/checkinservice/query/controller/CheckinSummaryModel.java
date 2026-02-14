package com.uexcel.checkinservice.query.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.checkinservice.CheckinStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CheckinSummaryModel {
    private String checkinId;
    private String paymentId;
    private String roomNumber;
    private String customerName;
    private String mobileNumber;
    private String nextOfKinMobileNumber;
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;
    private Double amountPaid;
    @Column(nullable = true)
    private String reservationId;
    private CheckinStatus checkinStatus;
    private String reason;
}
