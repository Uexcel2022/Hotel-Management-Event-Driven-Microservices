package com.uexcel.reservationservice.query.controller;

import com.uexcel.reservationservice.query.FindReservationByMobileNumberQuery;
import com.uexcel.reservationservice.query.FindReservationQuery;
import com.uexcel.reservationservice.query.ReservationSummary;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reservations",produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationQueryController {
    private final QueryGateway queryGateway;

    public ReservationQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationSummary> fetchReservation(@PathVariable String reservationId) {
        return ResponseEntity.ok(queryGateway.query(new FindReservationQuery(reservationId),
                ReservationSummary.class).join());
    }

    @GetMapping("/mobileNumber/{mobileNumber}")
    public ResponseEntity<List<ReservationSummary>> fetchReservationByMobileNumber(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(
                queryGateway.query(new FindReservationByMobileNumberQuery(mobileNumber),
                        ResponseTypes.multipleInstancesOf(ReservationSummary.class)).join());
    }

}
