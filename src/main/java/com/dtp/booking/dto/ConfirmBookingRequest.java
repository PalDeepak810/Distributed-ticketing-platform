package com.dtp.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmBookingRequest {
    @NotNull
    private Long bookingId;
}
