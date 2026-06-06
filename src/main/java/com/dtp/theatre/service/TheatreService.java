package com.dtp.theatre.service;

import com.dtp.theatre.dto.CreateScreenRequest;
import com.dtp.theatre.dto.CreateTheatreRequest;
import com.dtp.theatre.entity.Screen;
import com.dtp.theatre.entity.Theatre;
import com.dtp.theatre.repository.ScreenRepository;
import com.dtp.theatre.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

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
}
