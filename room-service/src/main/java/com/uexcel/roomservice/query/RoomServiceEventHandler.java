package com.uexcel.roomservice.query;

import com.uexcel.common.RoomStatus;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import com.uexcel.roomservice.entity.Room;
import com.uexcel.roomservice.command.inventory.RoomReservedEvent;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.command.inventory.RoomInventoryForDateCreatedEvent;
import com.uexcel.roomservice.command.room.RoomCreatedEvent;
import com.uexcel.roomservice.entity.RoomType;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import com.uexcel.roomservice.command.roomtype.RoomTypeCreatedEvent;
import com.uexcel.roomservice.query.checkin.RoomInventoryForDateUpdatedForCheckinEvent;
import com.uexcel.roomservice.query.checkin.RoomStatusUpdatedForCheckinEvent;
import jakarta.transaction.Transactional;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("room-group")
public class RoomServiceEventHandler {
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final RoomInventoryForDateRepository roomInventoryForDateRepository;

    public RoomServiceEventHandler(RoomTypeRepository roomTypeRepository,
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

    @Transactional
    @EventHandler
    public void onChange(RoomReservedEvent event) {
        RoomInventoryForDate reservedRooms =
                roomInventoryForDateRepository.findByRoomInventoryForDateId(
                        event.getRoomInventoryForDateId()
                );
        if (reservedRooms.getAvailableRooms() == 0) {
            throw new CommandExecutionException("No more room type available for reservation!", null); }

        reservedRooms.setAvailableRooms(
                reservedRooms.getAvailableRooms()-1
        );
        roomInventoryForDateRepository.save(reservedRooms);
    }

    @Transactional
    @EventHandler
    public void on(RoomInventoryForDateUpdatedForCheckinEvent event) {
        RoomInventoryForDate roomInventoryForDate =
                roomInventoryForDateRepository.findByRoomInventoryForDateId(
                        event.getRoomInventoryForDateId()
                );
        if(roomInventoryForDate.getAvailableRooms() == 0){
            throw new CommandExecutionException("Room type is no more available for check-in!", null);
        }
        roomInventoryForDate.setAvailableRooms( roomInventoryForDate.getAvailableRooms()-1);
        roomInventoryForDateRepository.save(roomInventoryForDate);
    }

    @Transactional
    @EventHandler
    public void on(RoomStatusUpdatedForCheckinEvent event) {
        Room room = roomRepository.findByRoomNumber(event.getRoomNumber());
        if(room.getRoomStatus().equals(RoomStatus.occupied) &&
                event.getRoomStatus().equals(RoomStatus.occupied)) {
            throw new CommandExecutionException("Room is not available for check-in!", null);
        }
        room.setRoomStatus(event.getRoomStatus());
        roomRepository.save(room);
    }
}


