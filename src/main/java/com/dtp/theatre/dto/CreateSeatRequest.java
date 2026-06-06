package com.dtp.theatre.dto;

import com.dtp.common.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSeatRequest {
    @NotBlank
    private String seatNumber;

    @NotBlank
    private String rowLabel;

    @NotNull
    private SeatType seatType;

    @NotNull
    private Long screenId;
}
