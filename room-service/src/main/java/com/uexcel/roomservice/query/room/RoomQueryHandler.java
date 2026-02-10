package com.uexcel.roomservice.query.room;

import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.entity.Room;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import com.uexcel.roomservice.query.reservationfordate.FindReservationForDateByRoomTypeId;
import com.uexcel.roomservice.query.reservationfordate.RoomInventoryForDateModel;
import com.uexcel.roomservice.query.reservationfordate.FindAllRoomTypeInventoryForDate;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomQueryHandler {
    private final RoomRepository roomRepository;

    public RoomQueryHandler(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @QueryHandler
    public List<RoomModel> getRoomByTypeAndStatus(FindRoomByRoomTypeIdAndRoomStatus query) {
           List<Room> roomModels = roomRepository
                   .findByRoomTypeIdAndRoomStatus(query.getRoomTypeId(), query.getRoomStatus());
           return resolveQuery(roomModels);
    }

    @QueryHandler
    public List<RoomModel> getRoomByType(FindRoomByTypeIdQuery query) {
        List<Room> roomModels = roomRepository
                .findByRoomTypeId(query.getRoomTyeId());
        return resolveQuery(roomModels);
    }

    @QueryHandler
    public RoomModel getRoomByNumber(FindRoomByNumberQuery query) {
        Room room = roomRepository.findByRoomNumber(query.getRoomNumber());
        RoomModel roomModel = new RoomModel();
        BeanUtils.copyProperties(room, roomModel);
        return roomModel;
    }


    private List<RoomModel> resolveQuery(List<Room> randoms) {
        ArrayList<RoomModel> roomModelsForDateModels = new ArrayList<>();
        randoms.forEach(random ->{
            RoomModel roomModel = new RoomModel();
            BeanUtils.copyProperties(random, roomModel);
            roomModelsForDateModels.add(roomModel);
        });
        return roomModelsForDateModels;
    }

}
