package com.uexcel.common.query;

import lombok.Data;

@Data
public class PaymentDetailsModel {
    private String paymentId;
    private String fullName;
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private Double amount;
}
