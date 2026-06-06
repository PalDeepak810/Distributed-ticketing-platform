package com.dtp.theatre.controller;

import com.dtp.theatre.dto.CreateTheatreRequest;
import com.dtp.theatre.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}