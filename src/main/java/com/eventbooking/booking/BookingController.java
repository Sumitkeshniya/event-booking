package com.eventbooking.booking;

import com.eventbooking.security.user.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookings")
@PreAuthorize("hasRole('CUSTOMER')")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * 1. Book tickets for an event
     * Triggers async booking confirmation
     */
    @PostMapping
    public ResponseEntity<Booking> bookTickets(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Booking booking = bookingService.bookTickets(
                request.getEventId(),
                request.getSeats(),
                request.getUserId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

}

