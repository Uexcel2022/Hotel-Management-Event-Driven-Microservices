package com.uexcel.checkinservice.query.controller;

import com.uexcel.checkinservice.query.FindUnCheckoutRoomsQuery;
import com.uexcel.common.query.FindRoomInfoQuery;
import com.uexcel.common.query.QueryResultSummaryForCheckin;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkins")
public class CheckinQueryController {
    private  final QueryGateway queryGateway;

    public CheckinQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @RequestMapping("/{roomTypeName}")
    public ResponseEntity<QueryResultSummaryForCheckin> getCheckin(@PathVariable String roomTypeName) {
      return ResponseEntity.ok(queryGateway.query(new FindRoomInfoQuery(roomTypeName),
              ResponseTypes.instanceOf(QueryResultSummaryForCheckin.class)).join());
    }

    @GetMapping("/uncheckouts")
    public ResponseEntity<List<CheckinSummaryModel>> findNotCheckoutRooms(
            @RequestParam(required = false) String roomNumber) {
        return ResponseEntity.ok(queryGateway.query(new FindUnCheckoutRoomsQuery(roomNumber),
                ResponseTypes.multipleInstancesOf(CheckinSummaryModel.class)).join());
    }

}
