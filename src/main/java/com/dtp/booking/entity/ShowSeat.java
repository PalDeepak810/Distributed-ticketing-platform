package com.dtp.booking.entity;

import com.dtp.common.enums.SeatStatus;
import com.dtp.theatre.entity.Seat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "show_seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"show_id","seat_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Version
    private Long version;
}
