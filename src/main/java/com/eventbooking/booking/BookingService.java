package com.eventbooking.booking;

import com.eventbooking.event.Event;
import com.eventbooking.event.EventRepository;
import com.eventbooking.notifications.NotificationService;
import com.eventbooking.user.User;
import com.eventbooking.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final EventRepository eventRepo;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepo,
            EventRepository eventRepo,
            NotificationService notificationService, UserRepository userRepository
    ) {
        this.bookingRepo = bookingRepo;
        this.eventRepo = eventRepo;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Booking bookTickets(Long eventId, int seats, Long customerId) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableSeats() < seats) {
            throw new RuntimeException("Not enough seats");
        }

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        event.setAvailableSeats(event.getAvailableSeats() - seats);

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setCustomer(customer);
        booking.setSeatsBooked(seats);
        booking.setBookedAt(LocalDateTime.now());

        bookingRepo.save(booking);

        notificationService.sendBookingConfirmation(customer, event);

        return booking;
    }

    public List<Booking> getBooking(){
        return bookingRepo.findAll();
    }
}

