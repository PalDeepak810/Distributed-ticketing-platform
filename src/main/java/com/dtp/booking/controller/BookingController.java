package com.dtp.booking.controller;

import com.dtp.booking.dto.ConfirmBookingRequest;
import com.dtp.booking.dto.CreateBookingResponse;
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
    public ResponseEntity<CreateBookingResponse> reserveSeats(
            @RequestBody
            ReserveSeatsRequest request
    ){
        return ResponseEntity.ok(
                bookingService.reserveSeats(request)
        );
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmBooking(
            @RequestBody
            ConfirmBookingRequest request
    ){
        return ResponseEntity.ok(
                bookingService.confirmBooking(
                        request.getBookingId()
                )
        );
    }

}
