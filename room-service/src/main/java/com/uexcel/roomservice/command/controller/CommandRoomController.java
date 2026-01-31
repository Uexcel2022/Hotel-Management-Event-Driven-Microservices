package com.uexcel.roomservice.command.controller;

import com.uexcel.roomservice.command.room.CreateRoomCommand;
import com.uexcel.roomservice.command.roomtype.CreateRoomTypeCommand;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.distributed.CommandDispatchException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@RestController
@RequestMapping(value = "/rooms",produces = MediaType.APPLICATION_JSON_VALUE)
public class CommandRoomController {
    private final CommandGateway  commandGateway;

    public CommandRoomController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/type")
    public ResponseEntity<String> createRoomType(@RequestBody RoomTypeModel roomTypeModel) {
        CreateRoomTypeCommand command = CreateRoomTypeCommand.builder()
                .roomTypeId(UUID.randomUUID().toString())
                .roomTypeName(roomTypeModel.getRoomTypeName())
                .quantity(roomTypeModel.getQuantity())
                .price(roomTypeModel.getPrice())
                .build();
        try {
            commandGateway.sendAndWait(command);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Room Type Created Successfully");
    }

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody RoomModel roomModel) {
        CreateRoomCommand command = CreateRoomCommand.builder()
                .number(roomModel.getRoomNumber())
                .roomTypeId(roomModel.getRoomTypeId())
                .build();
        try {
            commandGateway.sendAndWait(command);
        }catch (IllegalArgumentException e) {
           throw new IllegalArgumentException(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Room Type Created Successfully");

    }
}
