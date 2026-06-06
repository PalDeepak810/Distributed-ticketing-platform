package com.dtp.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "booking_seats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeat {
    @EmbeddedId
    private BookingSeatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("showSeatId")
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;
}
