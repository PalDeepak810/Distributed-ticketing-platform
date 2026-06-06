package com.dtp.booking.repository;

import com.dtp.booking.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findByShowId(Long showId);

    List<ShowSeat> findAllByIdIn(
            List<Long> ids
    );
}
