package com.uexcel.roomservice.query;

import com.uexcel.roomservice.command.entity.RoomInventoryForDate;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import com.uexcel.roomservice.query.controller.RoomInventoryForDateModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomQueryHandler {
    private final RoomInventoryForDateRepository roomInventoryForDateRepository;

    public RoomQueryHandler(RoomInventoryForDateRepository roomInventoryForDateRepository) {
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
    }
    @QueryHandler
    public List<RoomInventoryForDateModel> getRoomInventoryForDate(FindAllRoomTypeInventorForDate query) {
        ArrayList<RoomInventoryForDateModel> roomInventoryForDateModels = new ArrayList<>();
           List<RoomInventoryForDate> roomInventoryForDates =
                   roomInventoryForDateRepository.findAll();
        roomInventoryForDates.forEach(roomInventoryForDate ->{
                RoomInventoryForDateModel roomInventoryForDateModel = new RoomInventoryForDateModel();
                BeanUtils.copyProperties(roomInventoryForDate, roomInventoryForDateModel);
                roomInventoryForDateModels.add(roomInventoryForDateModel);
        });

        return roomInventoryForDateModels;
    }

}
