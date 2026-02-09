package com.uexcel.roomservice.query.controller;

import com.uexcel.roomservice.query.FindAllRoomTypeInventorForDate;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/reservations",produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomQueryController {
    private final QueryGateway queryGateway;

    public RoomQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public ResponseEntity<List<RoomInventoryForDateModel>> getAllRoomTypeInventoryForDate(){
       return ResponseEntity.ok(queryGateway.query(new FindAllRoomTypeInventorForDate(),
               ResponseTypes.multipleInstancesOf(RoomInventoryForDateModel.class)).join());
    }
}
