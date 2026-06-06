package com.dtp.booking.service;

import com.dtp.booking.dto.ReserveSeatsRequest;
import com.dtp.booking.entity.ShowSeat;
import com.dtp.booking.repository.ShowSeatRepository;
import com.dtp.common.enums.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final ShowSeatRepository showSeatRepository;

    public String reserveSeats(ReserveSeatsRequest request){
        List<ShowSeat> showSeats =
                showSeatRepository.findAllByIdIn(
                        request.getShowSeatIds()
                );

        if(showSeats.size()!=request.getShowSeatIds().size()){
            throw new RuntimeException("One or more seats not found");
        }

        for(ShowSeat seat:showSeats){
            if(seat.getStatus()!= SeatStatus.AVAILABLE){
                throw new RuntimeException("Seat already reserved or booked");
            }
        }

        for(ShowSeat seat:showSeats){
            seat.setStatus(SeatStatus.LOCKED);
        }

        showSeatRepository.saveAll(showSeats);
        return "Seats Reserved";
    }
}
