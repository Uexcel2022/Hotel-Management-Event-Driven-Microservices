package com.uexcel.paymentservice.query;


import com.uexcel.common.query.FindPaymentDetailsQuery;
import com.uexcel.common.query.PaymentDetailsModel;
import com.uexcel.paymentservice.entity.PaymentDetails;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PaymentQueryHandler {
    @QueryHandler
    public PaymentDetailsModel handle(FindPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setCardNumber("1234-2673-1920");
        paymentDetails.setFullName("Musa James");
        paymentDetails.setCvv("123");
        paymentDetails.setExpiryDate("12/23");
        paymentDetails.setAmount(1000.00);
        PaymentDetailsModel paymentDetailsModel = new PaymentDetailsModel();
        BeanUtils.copyProperties(paymentDetails, paymentDetailsModel);
        return paymentDetailsModel;
    }
}
