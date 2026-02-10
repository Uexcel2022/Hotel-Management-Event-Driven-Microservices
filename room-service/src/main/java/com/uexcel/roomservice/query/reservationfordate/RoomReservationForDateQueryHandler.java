package com.uexcel.roomservice.query.reservationfordate;

import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomReservationForDateQueryHandler {
    private final RoomInventoryForDateRepository roomInventoryForDateRepository;

    public RoomReservationForDateQueryHandler(RoomInventoryForDateRepository roomInventoryForDateRepository) {
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
    }
    @QueryHandler
    public List<RoomInventoryForDateModel> getRoomInventoryForDate(FindAllRoomTypeInventoryForDate query) {

           List<RoomInventoryForDate> roomInventoryForDates =
                   roomInventoryForDateRepository.findAll();
           return resolveQuery(roomInventoryForDates);
    }

    @QueryHandler
    public List<RoomInventoryForDateModel>
    getRoomInventoryForDateByRoomTypeId(FindReservationForDateByRoomTypeId query) {

        List<RoomInventoryForDate> roomInventoryForDates =
                roomInventoryForDateRepository.findByRoomTypeId(query.getRoomTypeId());
        return resolveQuery(roomInventoryForDates);
    }



    private List<RoomInventoryForDateModel> resolveQuery(List<RoomInventoryForDate> roomInventoryForDates) {
        ArrayList<RoomInventoryForDateModel> roomInventoryForDateModels = new ArrayList<>();
        roomInventoryForDates.forEach(roomInventoryForDate ->{
            RoomInventoryForDateModel roomInventoryForDateModel = new RoomInventoryForDateModel();
            BeanUtils.copyProperties(roomInventoryForDate, roomInventoryForDateModel);
            roomInventoryForDateModels.add(roomInventoryForDateModel);
        });

        return roomInventoryForDateModels;
    }

}
