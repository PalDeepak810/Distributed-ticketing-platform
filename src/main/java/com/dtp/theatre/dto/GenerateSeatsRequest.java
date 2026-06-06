package com.dtp.theatre.dto;

import com.dtp.common.enums.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateSeatsRequest {
    @NotNull
    @Positive
    private Integer rows;

    @NotNull
    @Positive
    private Integer seatsPerRow;

    @NotNull
    private SeatType seatType;
}
