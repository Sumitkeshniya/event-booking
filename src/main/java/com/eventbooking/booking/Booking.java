package com.eventbooking.booking;


import com.eventbooking.event.Event;
import com.eventbooking.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User customer;

    private int seatsBooked;
    private LocalDateTime bookedAt;
}
