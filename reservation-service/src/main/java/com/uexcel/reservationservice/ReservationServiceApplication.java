package com.uexcel.reservationservice;

import com.uexcel.reservationservice.command.interceptor.ReservationCommandInterceptor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Autowired
    public void registerProcessingGroup(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler(
                "reservation-group",config-> PropagatingErrorHandler.instance());
    }

    @Autowired
    public void registerInterceptor(ApplicationContext context, CommandGateway commandGateway){
        commandGateway.registerDispatchInterceptor(context.getBean(ReservationCommandInterceptor.class));
    }

}

//ReservationCommandInterceptor