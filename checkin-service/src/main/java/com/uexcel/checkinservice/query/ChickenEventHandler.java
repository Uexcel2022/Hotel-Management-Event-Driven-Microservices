package com.uexcel.checkinservice.query;

import com.uexcel.checkinservice.command.CheckinCalledEvent;
import com.uexcel.checkinservice.command.CheckinCreatedEvent;
import com.uexcel.checkinservice.command.CheckinRejectedEvent;
import com.uexcel.checkinservice.command.CheckinSucceededEvent;
import com.uexcel.checkinservice.entity.Checkin;
import com.uexcel.checkinservice.repository.CheckinRepository;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("checkin-group")
public class ChickenEventHandler {
    private final CheckinRepository checkinRepository;

    public ChickenEventHandler(CheckinRepository checkinRepository) {
        this.checkinRepository = checkinRepository;
    }
@EventHandler
    public void on(CheckinCreatedEvent checkinCreatedEvent) {
        Checkin checkin = new Checkin();
        BeanUtils.copyProperties(checkinCreatedEvent,checkin);
        checkinRepository.save(checkin);
    }

    @EventHandler
    public void on(CheckinRejectedEvent checkinRejectedEvent) {
       Checkin checkin = checkinRepository
               .findByCheckinId(checkinRejectedEvent.getCheckinId());
       if(checkin==null){
           throw new CommandExecutionException("Some thing went wrong!",null);
       }
       checkin.setCheckinStatus(checkinRejectedEvent.getCheckinStatus());
        checkinRepository.save(checkin);
    }

    @EventHandler
    public void on(CheckinSucceededEvent checkinSucceededEvent) {
        Checkin checkin = checkinRepository
                .findByCheckinId(checkinSucceededEvent.getCheckinId());
        checkin.setCheckinStatus(checkinSucceededEvent.getCheckinStatus());
        checkinRepository.save(checkin);
    }
    @EventHandler
    public void on(CheckinCalledEvent checkinCalledEvent) {
        Checkin checkin = checkinRepository
                .findByCheckinId(checkinCalledEvent.getCheckinId());
        checkin.setCheckinStatus(checkinCalledEvent.getCheckinStatus());
        checkinRepository.save(checkin);
    }

}
