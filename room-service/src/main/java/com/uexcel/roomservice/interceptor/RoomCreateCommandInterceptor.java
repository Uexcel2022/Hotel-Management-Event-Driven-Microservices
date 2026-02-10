package com.uexcel.roomservice.interceptor;

import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import com.uexcel.roomservice.command.room.CreateRoomCommand;
import com.uexcel.roomservice.command.roomtype.CreateRoomTypeCommand;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
@Component
public class RoomCreateCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{
    private final RoomTypeRepository  roomTypeRepository;
    private final RoomRepository roomRepository;

    public RoomCreateCommandInterceptor(RoomTypeRepository roomTypeRepository, RoomRepository roomRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public @NonNull BiFunction<Integer, CommandMessage<?>, CommandMessage<?>>
    handle(@NonNull List<? extends CommandMessage<?>> messages) {

        return (index, command)->{
            if(command.getPayload() instanceof CreateRoomCommand cRC){;
                if(!roomTypeRepository.existsByRoomTypeId(cRC.getRoomTypeId())){
                    throw new  IllegalArgumentException("Invalid Room Type ID");
                }else {
                    if(roomRepository.existsByRoomNumber(cRC.getRoomNumber())){
                        throw new  IllegalArgumentException("""
                                The room '%s' already exists.""".formatted(cRC.getRoomNumber()));

                    }
                }

            }

            if(command.getPayload() instanceof CreateRoomTypeCommand cRT){
                if(roomTypeRepository.existsByRoomTypeName(cRT.getRoomTypeName())){
                    throw new IllegalArgumentException("""
                                The room type '%s' already exists.""".formatted(cRT.getRoomTypeName()));
                }
            }

            return command;
        };
    }
}
