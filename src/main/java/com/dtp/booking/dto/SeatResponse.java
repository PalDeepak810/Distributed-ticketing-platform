package com.dtp.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long showSeatId;

    private String seatNumber;

    private String rowLabel;

    private String seatType;

    private String status;
}
