package com.dtp.payment.controller;

import com.dtp.payment.dto.CreatePaymentRequest;
import com.dtp.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(
            @RequestBody
            CreatePaymentRequest request
    ){
        return ResponseEntity.ok(
                paymentService.processPayment(
                        request
                )
        );
    }
}
