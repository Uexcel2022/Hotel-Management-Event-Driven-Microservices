package com.uexcel.roomservice.interceptor;


import com.uexcel.common.command.ReserveRoomCommand;
import com.uexcel.roomservice.command.entity.RoomInventoryForDate;
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

    public CommandHandlerInterceptor(RoomInventoryForDateRepository roomInventoryForDateRepository) {
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
    }

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> uow,
                         @NonNull InterceptorChain chain) throws Exception {

        CommandMessage<?> cmd = uow.getMessage();
        if (cmd.getPayload() instanceof ReserveRoomCommand command) {

            RoomInventoryForDate roomInventoryForDate = roomInventoryForDateRepository
                    .findByRoomInventoryForDateId(command.getRoomInventoryForDateId());

            if (roomInventoryForDate == null || LocalDate.now().isAfter(command.getBookingDate())) {
                throw new CommandExecutionException("Invalid room type reservation for date ID or Past date!", null);
            }

            boolean currentAvailableRooms = roomInventoryForDate.getAvailableRooms() < command.getBookedQuantity();
            if (currentAvailableRooms) {
                throw new CommandExecutionException("Insufficient number of available rooms!", null);
            }

            if (!roomInventoryForDate.getRoomTypeName().equalsIgnoreCase(command.getRoomTypeName()) ||
                    !roomInventoryForDate.getBookingDate().isEqual(command.getBookingDate()) ||
                    !roomInventoryForDate.getRoomTypeId().equals(command.getRoomTypeId())) {
                throw new CommandExecutionException("Room type or date mismatch!", null);
            }
        }

        return chain.proceed();
    }

}

