package com.uexcel.roomservice;

import com.uexcel.roomservice.command.interceptor.RoomCreateCommandInterceptor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RoomServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomServiceApplication.class, args);
	}

@Autowired
	public void registerInterceptor(ApplicationContext context, CommandGateway commandGateway){
		commandGateway.registerDispatchInterceptor(context.getBean(RoomCreateCommandInterceptor.class));
	}
    @Autowired
	public void registerProcessingGroup(EventProcessingConfigurer configurer){
		configurer.registerListenerInvocationErrorHandler(
				"room-group",config-> PropagatingErrorHandler.instance());
	}
}
