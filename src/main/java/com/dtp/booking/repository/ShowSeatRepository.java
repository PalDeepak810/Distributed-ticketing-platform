package com.dtp.booking.repository;

import com.dtp.booking.entity.ShowSeat;
import com.dtp.common.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findByShowId(Long showId);

    List<ShowSeat> findAllByIdIn(
            List<Long> ids
    );

    List<ShowSeat> findByLockedByUserId(Long userId);

    List<ShowSeat> findByStatusAndLockedAtBefore(
            SeatStatus status,
            LocalDateTime time
    );
}
