package com.dtp.theatre.controller;

import com.dtp.theatre.dto.CreateSeatRequest;
import com.dtp.theatre.dto.CreateTheatreRequest;
import com.dtp.theatre.dto.GenerateSeatsRequest;
import com.dtp.theatre.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public ResponseEntity<Long> createTheatre(
            @RequestBody
            CreateTheatreRequest request
    ) {

        return ResponseEntity.ok(
                theatreService.createTheatre(
                        request
                )
        );
    }

    @PostMapping("/seats")
    public ResponseEntity<Long> createSeat(
            @RequestBody
            CreateSeatRequest request
    ) {

        return ResponseEntity.ok(
                theatreService.createSeat(
                        request
                )
        );
    }


    @PostMapping("/screens/{screenId}/generate-seats")
    public ResponseEntity<Integer> generateSeats(
            @PathVariable Long screenId,
            @RequestBody GenerateSeatsRequest request
    ) {
        return ResponseEntity.ok(
                theatreService.generateSeats(
                        screenId,
                        request
                )
        );
    }
}