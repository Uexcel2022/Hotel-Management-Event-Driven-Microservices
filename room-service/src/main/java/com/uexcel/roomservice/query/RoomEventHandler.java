package com.uexcel.roomservice.query;

import com.uexcel.common.event.PublishRoomReservedEvent;
import com.uexcel.roomservice.command.entity.RoomInventoryForDate;
import com.uexcel.roomservice.command.entity.Room;
import com.uexcel.roomservice.command.inventory.RoomInventoryForDateReservedEvent;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.command.inventory.RoomInventoryForDateCreatedEvent;
import com.uexcel.roomservice.command.room.RoomCreatedEvent;
import com.uexcel.roomservice.command.entity.RoomType;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import com.uexcel.roomservice.command.roomtype.RoomTypeCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("room-group")
public class RoomEventHandler {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final RoomInventoryForDateRepository roomInventoryForDateRepository;

    public RoomEventHandler(RoomTypeRepository roomTypeRepository,
                            RoomRepository repository, RoomRepository roomRepository,
                            RoomInventoryForDateRepository roomInventoryForDateRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
    }

    @EventHandler
    public void onCreate(RoomCreatedEvent command) {
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


    @EventHandler
    public void onChange(RoomInventoryForDateCreatedEvent event) {
        RoomInventoryForDate roomInventoryForDate = new RoomInventoryForDate();
        BeanUtils.copyProperties(event, roomInventoryForDate);
        roomInventoryForDateRepository.save(roomInventoryForDate);
    }

    @EventHandler
    public void onChange(RoomInventoryForDateReservedEvent event, EventBus eventBus) {
        RoomInventoryForDate reservedRooms =
                roomInventoryForDateRepository.findByRoomInventoryForDateId(event.getRoomInventoryForDateId());
        reservedRooms.setAvailableRooms(
                reservedRooms.getAvailableRooms()
                        - event.getBookedQuantity()
        );
                roomInventoryForDateRepository.save(reservedRooms);

        PublishRoomReservedEvent publishRoomReservedEvent = new PublishRoomReservedEvent();
        BeanUtils.copyProperties(event, publishRoomReservedEvent);
        eventBus.publish(GenericEventMessage.asEventMessage(publishRoomReservedEvent));
    }
}


