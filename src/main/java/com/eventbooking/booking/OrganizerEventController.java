package com.eventbooking.booking;

import com.eventbooking.event.Event;
import com.eventbooking.event.EventRequest;
import com.eventbooking.event.EventService;
import com.eventbooking.exception.ResponseGenerator;
import com.eventbooking.security.user.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizer/events")
@PreAuthorize("hasRole('ORGANIZER')")
public class OrganizerEventController {

    private final EventService eventService;
    private final BookingService bookingService;

    public OrganizerEventController(EventService eventService, BookingService bookingService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
    }

    /**
     * 1. Create a new event
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Event event = eventService.addEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    /**
     * 2. Update an existing event
     * Triggers background notification to booked customers
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Event updatedEvent =
                eventService.updateEvent(eventId, request);

        return ResponseEntity.ok(updatedEvent);
    }

    @GetMapping("/booking-list")
    public ResponseEntity<Object> getBooking() {
        return ResponseGenerator.generateResponse("Booked successfully", HttpStatus.OK,bookingService.getBooking());
    }
}
