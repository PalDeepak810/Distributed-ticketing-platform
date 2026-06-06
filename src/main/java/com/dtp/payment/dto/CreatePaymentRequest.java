package com.dtp.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentRequest {
    private Long bookingId;

    private boolean success;
}
