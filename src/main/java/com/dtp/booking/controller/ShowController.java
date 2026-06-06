package com.dtp.booking.controller;

import com.dtp.booking.dto.CreateShowRequest;
import com.dtp.booking.dto.SeatResponse;
import com.dtp.booking.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;

    @PostMapping
    public ResponseEntity<Long> createShow(
            @RequestBody
            CreateShowRequest request
    ){
        return ResponseEntity.ok(
                showService.createShow(request)
        );
    }

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<SeatResponse>>
    getSeats(
            @PathVariable Long showId
    ) {

        return ResponseEntity.ok(
                showService.getSeats(showId)
        );
    }
}
