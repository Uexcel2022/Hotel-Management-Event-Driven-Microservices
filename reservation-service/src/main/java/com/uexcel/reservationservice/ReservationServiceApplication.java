package com.uexcel.reservationservice;

import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Autowired
    public void registerProcessingGroup(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler(
                "booking-group",config-> PropagatingErrorHandler.instance());
    }

}
