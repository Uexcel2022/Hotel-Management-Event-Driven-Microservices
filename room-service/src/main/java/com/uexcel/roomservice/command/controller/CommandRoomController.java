package com.uexcel.roomservice.command.controller;

import com.uexcel.roomservice.command.entity.RoomType;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import com.uexcel.roomservice.command.inventory.CreateRoomInventoryForDateCommand;
import com.uexcel.roomservice.command.room.CreateRoomCommand;
import com.uexcel.roomservice.command.roomtype.CreateRoomTypeCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;
@RestController
@RequestMapping(value = "/rooms",produces = MediaType.APPLICATION_JSON_VALUE)
public class CommandRoomController {
    private final CommandGateway  commandGateway;
    private final RoomTypeRepository roomTypeRepository;

    public CommandRoomController(CommandGateway commandGateway,
                                 RoomTypeRepository roomTypeRepository) {
        this.commandGateway = commandGateway;
        this.roomTypeRepository = roomTypeRepository;
    }

    @PostMapping("/types")
    public ResponseEntity<String> createRoomType(@RequestBody CreateRoomTypeModel createRoomTypeModel) {
        CreateRoomTypeCommand command = CreateRoomTypeCommand.builder()
                .roomTypeId(UUID.randomUUID().toString())
                .roomTypeName(createRoomTypeModel.getRoomTypeName())
                .quantity(createRoomTypeModel.getQuantity())
                .price(createRoomTypeModel.getPrice())
                .build();
        try {
            commandGateway.sendAndWait(command);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Room Type Created Successfully");
    }

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody CreateRoomModel createRoomModel) {
        CreateRoomCommand command = CreateRoomCommand.builder()
                .number(createRoomModel.getRoomNumber())
                .roomTypeId(createRoomModel.getRoomTypeId())
                .build();
        try {
            commandGateway.sendAndWait(command);
        }catch (IllegalArgumentException e) {
           throw new IllegalArgumentException(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Room Created Successfully");

    }

    @PostMapping("/types/reservation")
    public ResponseEntity<String> createReservation(@RequestBody CreateReservationModel createReservationModel) {
        RoomType roomType = roomTypeRepository.findByRoomTypeId(createReservationModel.getRoomTypeId());
        if(roomType == null) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND,"Room Type Not Found");
        }

        int numberOfDays = 365 - createReservationModel.getBookingDate().getDayOfYear();
        for(int i = 0; i < numberOfDays ; i++) {
            CreateRoomInventoryForDateCommand command = CreateRoomInventoryForDateCommand.builder()
                    .roomInventoryForDateId(UUID.randomUUID().toString())
                    .bookingDate(createReservationModel.getBookingDate().plusDays(i))
                    .availableRooms(roomType.getQuantity())
                    .roomTypeId(roomType.getRoomTypeId())
                    .roomTypeName(roomType.getRoomTypeName())
                    .price(roomType.getPrice())
                    .build();
            try {
                commandGateway.sendAndWait(command);
            } catch (Exception e) {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Room Reservations created Successfully");
    }

}
