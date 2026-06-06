package com.dtp.booking.dto;

import com.dtp.common.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponse {
    private Long bookingId;

    private BookingStatus status;
}
