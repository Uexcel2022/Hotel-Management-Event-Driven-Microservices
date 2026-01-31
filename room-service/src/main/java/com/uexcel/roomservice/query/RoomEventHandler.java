package com.uexcel.roomservice.query;

import com.uexcel.roomservice.command.entity.Room;
import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.command.room.RoomCreatedEvent;
import com.uexcel.roomservice.command.roomtype.CreateRoomTypeCommand;
import com.uexcel.roomservice.command.entity.RoomType;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import com.uexcel.roomservice.command.roomtype.RoomTypeCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoomEventHandler {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository  roomRepository;

    public RoomEventHandler(RoomTypeRepository roomTypeRepository, RoomRepository repository, RoomRepository roomRepository) {
        this.roomTypeRepository = roomTypeRepository;

        this.roomRepository = roomRepository;
    }

    @EventHandler
    public void  onCreate(RoomCreatedEvent command) {
        Room room = new Room();
        BeanUtils.copyProperties(command, room);
        roomRepository.save(room);
    }

    @EventHandler
    public void onChange(RoomTypeCreatedEvent event) {
        RoomType roomType = new RoomType();
        BeanUtils.copyProperties(event, roomType);
        roomTypeRepository.save(roomType);
    }
}
