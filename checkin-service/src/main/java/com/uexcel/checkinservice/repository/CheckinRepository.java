package com.uexcel.checkinservice.repository;

import com.uexcel.checkinservice.CheckinStatus;
import com.uexcel.checkinservice.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CheckinRepository extends JpaRepository<Checkin,String> {
    Checkin findByCheckinId(String checkinId);
    Checkin findByRoomNumberAndCheckoutAt(String roomNumber, LocalDateTime checkoutAt);
    List<Checkin> findByCheckoutAtIsNullAndCheckinStatus(CheckinStatus checkinStatus);
}
