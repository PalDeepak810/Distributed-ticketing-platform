package com.dtp.theatre.dto;

import com.dtp.common.enums.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSeatRequest {
    private String seatNumber;

    private String rowLabel;

    private SeatType seatType;

    private Long screenId;
}
