package com.dtp.booking.scheduler;

import com.dtp.booking.entity.Booking;
import com.dtp.booking.entity.BookingSeat;
import com.dtp.booking.entity.ShowSeat;
import com.dtp.booking.repository.BookingRepository;
import com.dtp.booking.repository.BookingSeatRepository;
import com.dtp.booking.repository.ShowSeatRepository;
import com.dtp.common.enums.BookingStatus;
import com.dtp.common.enums.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SeatLockCleanupScheduler {

    private final ShowSeatRepository showSeatRepository;

    private final BookingSeatRepository bookingSeatRepository;

    private final BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredLocks() {

        LocalDateTime expiryTime =
                LocalDateTime.now()
                        .minusMinutes(5);

        List<ShowSeat> expiredSeats =
                showSeatRepository
                        .findByStatusAndLockedAtBefore(
                                SeatStatus.LOCKED,
                                expiryTime
                        );

        Set<Booking> bookingsToUpdate =
                new HashSet<>();

        for (ShowSeat seat : expiredSeats) {

            List<BookingSeat> bookingSeats =
                    bookingSeatRepository
                            .findByShowSeatId(
                                    seat.getId()
                            );

            for (BookingSeat bookingSeat : bookingSeats) {

                Booking booking =
                        bookingSeat.getBooking();

                if (booking.getStatus()
                        == BookingStatus.PENDING) {

                    booking.setStatus(
                            BookingStatus.FAILED
                    );

                    bookingsToUpdate.add(
                            booking
                    );
                }
            }

            seat.setStatus(
                    SeatStatus.AVAILABLE
            );

            seat.setLockedAt(
                    null
            );

            seat.setLockedByUserId(
                    null
            );
        }

        bookingRepository.saveAll(
                bookingsToUpdate
        );

        showSeatRepository.saveAll(
                expiredSeats
        );
    }
}