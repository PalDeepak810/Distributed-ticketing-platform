package com.dtp.booking.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class BookingSeatId {
    private Long bookingId;
    private Long showSeatId;
}
