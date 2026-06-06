package com.dtp.booking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReserveSeatsRequest {
    @NotNull
    private Long userId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> showSeatIds;
}
