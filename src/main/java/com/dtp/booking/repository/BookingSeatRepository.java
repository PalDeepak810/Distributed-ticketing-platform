package com.dtp.booking.repository;

import com.dtp.booking.entity.BookingSeat;
import com.dtp.booking.entity.BookingSeatId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, BookingSeatId> {
    List<BookingSeat> findByBookingId(Long bookingId);
}
