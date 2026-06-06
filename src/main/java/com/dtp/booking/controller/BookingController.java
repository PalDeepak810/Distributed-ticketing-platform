package com.dtp.booking.controller;

import com.dtp.booking.dto.ReserveSeatsRequest;
import com.dtp.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveSeats(
            @RequestBody
            ReserveSeatsRequest request
    ){
        return ResponseEntity.ok(
                bookingService.reserveSeats(request)
        );
    }

}
