package com.uexcel.paymentservice.entity;

import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;
    private String fullName;
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private Double amount;

}
