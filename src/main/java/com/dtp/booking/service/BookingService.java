package com.dtp.booking.service;

import com.dtp.booking.dto.CreateBookingResponse;
import com.dtp.booking.dto.ReserveSeatsRequest;
import com.dtp.booking.entity.*;
import com.dtp.booking.repository.BookingRepository;
import com.dtp.booking.repository.BookingSeatRepository;
import com.dtp.booking.repository.ShowSeatRepository;
import com.dtp.common.enums.BookingStatus;
import com.dtp.common.enums.SeatStatus;
import com.dtp.user.entity.User;
import com.dtp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    private final ShowSeatRepository showSeatRepository;

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final BookingSeatRepository bookingSeatRepository;

    @Transactional
    public CreateBookingResponse reserveSeats(
            ReserveSeatsRequest request
    ) {

        List<ShowSeat> showSeats =
                showSeatRepository.findAllByIdIn(
                        request.getShowSeatIds()
                );

        if (showSeats.size() != request.getShowSeatIds().size()) {
            throw new RuntimeException(
                    "One or more seats not found"
            );
        }

        for (ShowSeat seat : showSeats) {

            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException(
                        "Seat already reserved or booked"
                );
            }
        }

        User user =
                userRepository.findById(
                                request.getUserId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        LocalDateTime now = LocalDateTime.now();

        for (ShowSeat seat : showSeats) {

            seat.setStatus(
                    SeatStatus.LOCKED
            );

            seat.setLockedByUserId(
                    request.getUserId()
            );

            seat.setLockedAt(now);
        }

        showSeatRepository.saveAll(showSeats);

        Show show =
                showSeats.get(0)
                        .getShow();

        BigDecimal totalAmount =
                show.getBasePrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        showSeats.size()
                                )
                        );

        Booking booking = new Booking();

        booking.setUser(user);

        booking.setShow(show);

        booking.setStatus(
                BookingStatus.PENDING
        );

        booking.setBookingTime(now);

        booking.setTotalAmount(
                totalAmount
        );

        booking =
                bookingRepository.save(
                        booking
                );

        List<BookingSeat> bookingSeats =
                new ArrayList<>();

        for (ShowSeat showSeat : showSeats) {

            BookingSeat bookingSeat =
                    new BookingSeat();

            BookingSeatId id =
                    new BookingSeatId(
                            booking.getId(),
                            showSeat.getId()
                    );

            bookingSeat.setId(id);

            bookingSeat.setBooking(
                    booking
            );

            bookingSeat.setShowSeat(
                    showSeat
            );

            bookingSeats.add(
                    bookingSeat
            );
        }

        try{
            showSeatRepository.saveAll(showSeats);
        }catch (ObjectOptimisticLockingFailureException ex){
            throw new RuntimeException(
                    "Seat was reserved by another user"
            );
        }

        return new CreateBookingResponse(
                booking.getId(),
                booking.getStatus()
        );
    }
    @Transactional
    public String confirmBooking(
            Long bookingId
    ) {

        Booking booking =
                bookingRepository.findById(
                                bookingId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Booking not found"
                                )
                        );

        if (booking.getStatus()
                != BookingStatus.PENDING) {

            throw new RuntimeException(
                    "Booking already processed"
            );
        }

        List<BookingSeat> bookingSeats =
                bookingSeatRepository
                        .findByBookingId(
                                bookingId
                        );

        List<ShowSeat> seatsToUpdate =
                new ArrayList<>();

        for (BookingSeat bookingSeat : bookingSeats) {

            ShowSeat showSeat =
                    bookingSeat.getShowSeat();

            showSeat.setStatus(
                    SeatStatus.BOOKED
            );

            showSeat.setLockedAt(null);

            showSeat.setLockedByUserId(null);

            seatsToUpdate.add(
                    showSeat
            );
        }

        showSeatRepository.saveAll(
                seatsToUpdate
        );

        booking.setStatus(
                BookingStatus.CONFIRMED
        );

        bookingRepository.save(
                booking
        );

        return "Booking Confirmed";
    }
}
