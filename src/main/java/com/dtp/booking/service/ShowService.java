package com.dtp.booking.service;

import com.dtp.booking.dto.CreateShowRequest;
import com.dtp.booking.dto.SeatResponse;
import com.dtp.booking.entity.Show;
import com.dtp.booking.entity.ShowSeat;
import com.dtp.booking.repository.ShowRepository;
import com.dtp.booking.repository.ShowSeatRepository;
import com.dtp.common.enums.SeatStatus;
import com.dtp.movie.entity.Movie;
import com.dtp.movie.repository.MovieRepository;
import com.dtp.theatre.entity.Screen;
import com.dtp.theatre.entity.Seat;
import com.dtp.theatre.repository.ScreenRepository;
import com.dtp.theatre.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowService {
    private final MovieRepository movieRepository;

    private final ScreenRepository screenRepository;

    private SeatRepository seatRepository;

    private final ShowRepository showRepository;

    private final ShowSeatRepository showSeatRepository;

    public Long createShow(CreateShowRequest request){
        Movie movie =
                movieRepository.findById(
                        request.getMovieId()
                ).orElseThrow();

        Screen screen =
                screenRepository.findById(
                        request.getScreenid()
                ).orElseThrow();

        Show show = new Show();

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setBasePrice(request.getBasePrice());

        show = showRepository.save(show);

        List<Seat> seats =
                seatRepository.findByScreenId(
                        screen.getId()
                );

        List<ShowSeat> showSeats =
                new ArrayList<>();

        for(Seat seat:seats){
            ShowSeat showSeat = new ShowSeat();

            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setStatus(SeatStatus.AVAILABLE);

            showSeats.add(showSeat);
        }
        showSeatRepository.saveAll(showSeats);
        return show.getId();
    }

    public List<SeatResponse> getSeats(Long showId) {

        List<ShowSeat> showSeats =
                showSeatRepository.findByShowId(showId);

        return showSeats.stream()
                .map(showSeat -> new SeatResponse(
                        showSeat.getId(),
                        showSeat.getSeat().getSeatNumber(),
                        showSeat.getSeat().getRowLabel(),
                        showSeat.getSeat().getSeatType().name(),
                        showSeat.getStatus().name()
                ))
                .toList();
    }
}
