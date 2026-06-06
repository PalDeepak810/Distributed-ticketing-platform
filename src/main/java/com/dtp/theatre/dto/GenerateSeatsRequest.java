package com.dtp.theatre.dto;

import com.dtp.common.enums.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateSeatsRequest {
    private Integer rows;

    private Integer seatsPerRow;

    private SeatType seatType;
}
