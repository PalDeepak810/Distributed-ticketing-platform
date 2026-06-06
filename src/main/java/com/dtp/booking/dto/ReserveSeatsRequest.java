package com.dtp.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReserveSeatsRequest {
    private Long userId;

    private List<Long> showSeatIds;
}
