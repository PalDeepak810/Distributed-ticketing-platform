package com.dtp.payment.service;

import com.dtp.booking.entity.Booking;
import com.dtp.booking.entity.BookingSeat;
import com.dtp.booking.entity.ShowSeat;
import com.dtp.booking.repository.BookingRepository;
import com.dtp.booking.repository.BookingSeatRepository;
import com.dtp.booking.repository.ShowSeatRepository;
import com.dtp.common.enums.BookingStatus;
import com.dtp.common.enums.PaymentStatus;
import com.dtp.common.enums.SeatStatus;
import com.dtp.payment.dto.CreatePaymentRequest;
import com.dtp.payment.entity.Payment;
import com.dtp.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public String processPayment(
            CreatePaymentRequest request
    ) {

        Booking booking =
                bookingRepository.findById(
                                request.getBookingId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Booking not found"
                                )
                        );

        List<BookingSeat> bookingSeats =
                bookingSeatRepository
                        .findByBookingId(
                                booking.getId()
                        );

        Payment payment =
                new Payment();

        payment.setBooking(
                booking
        );

        payment.setAmount(
                booking.getTotalAmount()
        );

        payment.setPaymentReference(
                UUID.randomUUID()
                        .toString()
        );

        List<ShowSeat> seatsToUpdate =
                new ArrayList<>();

        if (request.isSuccess()) {

            payment.setStatus(
                    PaymentStatus.SUCCESS
            );

            booking.setStatus(
                    BookingStatus.CONFIRMED
            );

            for (BookingSeat bookingSeat : bookingSeats) {

                ShowSeat showSeat =
                        bookingSeat.getShowSeat();

                showSeat.setStatus(
                        SeatStatus.BOOKED
                );

                showSeat.setLockedAt(
                        null
                );

                showSeat.setLockedByUserId(
                        null
                );

                seatsToUpdate.add(
                        showSeat
                );
            }

        } else {

            payment.setStatus(
                    PaymentStatus.FAILED
            );

            booking.setStatus(
                    BookingStatus.FAILED
            );

            for (BookingSeat bookingSeat : bookingSeats) {

                ShowSeat showSeat =
                        bookingSeat.getShowSeat();

                showSeat.setStatus(
                        SeatStatus.AVAILABLE
                );

                showSeat.setLockedAt(
                        null
                );

                showSeat.setLockedByUserId(
                        null
                );

                seatsToUpdate.add(
                        showSeat
                );
            }
        }

        showSeatRepository.saveAll(
                seatsToUpdate
        );

        bookingRepository.save(
                booking
        );

        paymentRepository.save(
                payment
        );

        return payment.getStatus().name();
    }
}
