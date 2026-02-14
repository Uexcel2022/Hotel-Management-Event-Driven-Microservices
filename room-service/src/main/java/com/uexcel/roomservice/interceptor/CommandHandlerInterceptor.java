package com.uexcel.roomservice.interceptor;


import com.uexcel.common.RoomStatus;
import com.uexcel.common.command.ReserveRoomCommand;
import com.uexcel.common.command.UpdateRoomForCheckinCommand;
import com.uexcel.common.command.UpdateRoomInventoryForDateForCheckinCommand;
import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.entity.Room;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommandHandlerInterceptor
        implements MessageHandlerInterceptor<CommandMessage<?>> {

    private final RoomInventoryForDateRepository roomInventoryForDateRepository;
    private  final RoomRepository roomRepository;

    public CommandHandlerInterceptor(RoomInventoryForDateRepository roomInventoryForDateRepository, RoomRepository roomRepository) {
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> uow,
                         @NonNull InterceptorChain chain) throws Exception {

        CommandMessage<?> cmd = uow.getMessage();
        if (cmd.getPayload() instanceof ReserveRoomCommand command) {

            RoomInventoryForDate roomInventoryForDate = roomInventoryForDateRepository
                    .findByRoomInventoryForDateId(command.getRoomInventoryForDateId());

            if (roomInventoryForDate == null || LocalDate.now().isAfter(command.getBookedDate())) {
                throw new CommandExecutionException("Invalid room type reservation for date ID or Past date!", null);
            }

            if (roomInventoryForDate.getAvailableRooms() < 1) {
                throw new CommandExecutionException("The room type is currently unavailable!", null);
            }

            if (!roomInventoryForDate.getRoomTypeName().equalsIgnoreCase(command.getRoomTypeName()) ||
                    !roomInventoryForDate.getAvailabilityDate().isEqual(command.getBookedDate()) ||
                    !roomInventoryForDate.getRoomTypeId().equals(command.getRoomTypeId())) {
                throw new CommandExecutionException("Room type or date mismatch!", null);
            }
        }

        if(cmd.getPayload() instanceof UpdateRoomInventoryForDateForCheckinCommand command) {
            RoomInventoryForDate roomInventoryForDate = roomInventoryForDateRepository
                    .findByRoomInventoryForDateId(command.getRoomInventoryForDateId());
            if(roomInventoryForDate == null) {
//                updateRoomStatus(command.getRoomNumber());
                throw new CommandExecutionException("Invalid Room Inventory For Date ID!", null);
            }

            if(!roomInventoryForDate.getAvailabilityDate().isEqual(LocalDate.now())) {
//                updateRoomStatus(command.getRoomNumber());
                throw new CommandExecutionException("Invalid date!", null);
            }

            if(!roomInventoryForDate.getRoomTypeName().equalsIgnoreCase(command.getRoomTypeName())) {
//                updateRoomStatus(command.getRoomNumber());
                throw new CommandExecutionException("Room type name mismatched!", null);
            }

            if(roomInventoryForDate.getAvailableRooms() == 0 && command.getReservationId()==null) {
//                updateRoomStatus(command.getRoomNumber());
                throw new CommandExecutionException("The room type is currently unavailable!", null);
            }
        }

        if(cmd.getPayload() instanceof UpdateRoomForCheckinCommand command) {
            Room room = roomRepository.findByRoomNumber(command.getRoomNumber());
            if(room == null) {
                throw new CommandExecutionException("Invalid room number!", null);
            }
            if(room.getRoomStatus().equals(RoomStatus.occupied) &&
                    command.getRoomStatus().equals(RoomStatus.occupied)) {
                throw new CommandExecutionException("Room is not available for check-in!", null);
            }
        }

        return chain.proceed();
    }

    private void updateRoomStatus(String roomName){
        Room room = roomRepository.findByRoomNumber(roomName);
        room.setRoomStatus(RoomStatus.available);
        roomRepository.save(room);

    }

}

