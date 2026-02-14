package com.uexcel.checkinservice.query;

import com.uexcel.checkinservice.CheckinStatus;
import com.uexcel.checkinservice.entity.Checkin;
import com.uexcel.checkinservice.query.controller.CheckinSummaryModel;
import com.uexcel.checkinservice.repository.CheckinRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CheckinQueryHandler {

    private final CheckinRepository checkinRepository;

    public CheckinQueryHandler(CheckinRepository checkinRepository) {
        this.checkinRepository = checkinRepository;
    }

    @QueryHandler
    public CheckinSummaryModel on(FindCheckinByIdQuery findCheckinByIdQuery) {
        CheckinSummaryModel checkinSummaryModel = new CheckinSummaryModel();
        Checkin checkin = checkinRepository.findByCheckinId(findCheckinByIdQuery.getCheckinId());
        BeanUtils.copyProperties(checkin, checkinSummaryModel);
        return checkinSummaryModel;
    }

    @QueryHandler
    public CheckinSummaryModel on(FindCheckinByRoomNumberQuery findCheckinByRoomNumberQuery) {
        CheckinSummaryModel checkinSummaryModel = new CheckinSummaryModel();
        Checkin checkin = checkinRepository.findByRoomNumberAndCheckoutAt
                (findCheckinByRoomNumberQuery.getRoomNumber(),null);
        BeanUtils.copyProperties(checkin, checkinSummaryModel);
        return checkinSummaryModel;
    }

    @QueryHandler
    public List<CheckinSummaryModel> on(FindUnCheckoutRoomsQuery findUnCheckoutRoomsQuery) {
        List<CheckinSummaryModel> checkinSummaryModelList = new  ArrayList<>();
        List<Checkin> checkins = checkinRepository.findByCheckoutAtIsNullAndCheckinStatus(CheckinStatus.succeeded);
        checkins.forEach(checkin -> {
            if(findUnCheckoutRoomsQuery.getRoomNumber()==null) {

                CheckinSummaryModel checkinSummaryModel = new CheckinSummaryModel();
                checkinSummaryModel.setCheckinId(checkin.getCheckinId());
                checkinSummaryModel.setRoomNumber(checkin.getRoomNumber());
                checkinSummaryModel.setCustomerName(checkin.getCustomerName());
                checkinSummaryModel.setCheckinStatus(checkin.getCheckinStatus());
                checkinSummaryModelList.add(checkinSummaryModel);

            }else if(findUnCheckoutRoomsQuery.getRoomNumber().equals(checkin.getRoomNumber())) {
                CheckinSummaryModel checkinSummaryModel = new CheckinSummaryModel();
                checkinSummaryModel.setCheckinId(checkin.getCheckinId());
                checkinSummaryModel.setRoomNumber(checkin.getRoomNumber());
                checkinSummaryModel.setCustomerName(checkin.getCustomerName());
                checkinSummaryModel.setCheckinStatus(checkin.getCheckinStatus());
                checkinSummaryModelList.add(checkinSummaryModel);
            }
        });
        return checkinSummaryModelList;
    }
}
