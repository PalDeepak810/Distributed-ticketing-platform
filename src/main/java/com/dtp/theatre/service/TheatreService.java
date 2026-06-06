package com.dtp.theatre.service;

import com.dtp.theatre.dto.CreateScreenRequest;
import com.dtp.theatre.dto.CreateSeatRequest;
import com.dtp.theatre.dto.CreateTheatreRequest;
import com.dtp.theatre.dto.GenerateSeatsRequest;
import com.dtp.theatre.entity.Screen;
import com.dtp.theatre.entity.Seat;
import com.dtp.theatre.entity.Theatre;
import com.dtp.theatre.repository.ScreenRepository;
import com.dtp.theatre.repository.SeatRepository;
import com.dtp.theatre.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    public Long createTheatre(
            CreateTheatreRequest request
    ){
        Theatre theatre = new Theatre();

        theatre.setName(request.getName());
        theatre.setCity(request.getCity());
        theatre.setAddress(request.getAddress());

        return theatreRepository
                .save(theatre)
                .getId();
    }


    public Long createScreen(
            CreateScreenRequest request
    ){
        Theatre theatre =
                theatreRepository.findById(
                        request.getTheatreId()
                ).orElseThrow(
                        ()->new RuntimeException(
                                "Theatre not found"
                        )
                );

        Screen screen = new Screen();

        screen.setName(request.getName());
        screen.setTheatre(
                theatre
        );

        return screenRepository.save(screen).getId();
    }

    public Long createSeat(
            CreateSeatRequest request
    ) {

        Screen screen =
                screenRepository.findById(
                        request.getScreenId()
                ).orElseThrow(
                        () -> new RuntimeException(
                                "Screen not found"
                        )
                );

        Seat seat = new Seat();

        seat.setSeatNumber(
                request.getSeatNumber()
        );

        seat.setRowLabel(
                request.getRowLabel()
        );

        seat.setSeatType(
                request.getSeatType()
        );

        seat.setScreen(
                screen
        );

        return seatRepository
                .save(seat)
                .getId();
    }



    @Transactional
    public Integer generateSeats(
            Long screenId,
            GenerateSeatsRequest request
    ) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(
                        () -> new RuntimeException("Screen not found")
                );

        List<Seat> seats = new ArrayList<>();

        for (int row = 0; row < request.getRows(); row++) {

            char rowLabel = (char) ('A' + row);

            for (int seatNo = 1;
                 seatNo <= request.getSeatsPerRow();
                 seatNo++) {

                Seat seat = new Seat();

                seat.setRowLabel(
                        String.valueOf(rowLabel)
                );

                seat.setSeatNumber(
                        rowLabel + String.valueOf(seatNo)
                );

                seat.setSeatType(
                        request.getSeatType()
                );

                seat.setScreen(screen);

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);

        return seats.size();
    }
}
