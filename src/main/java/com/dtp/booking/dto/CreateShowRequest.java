package com.dtp.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateShowRequest {
    private Long movieId;

    private Long screenid;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal basePrice;
}
