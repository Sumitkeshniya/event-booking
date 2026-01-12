package com.eventbooking.event;

import com.eventbooking.booking.Booking;
import com.eventbooking.booking.BookingRepository;
import com.eventbooking.exception.ResourceNotFoundException;
import com.eventbooking.notifications.NotificationService;
import com.eventbooking.user.User;
import com.eventbooking.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final BookingRepository bookingRepo;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public EventService(
            EventRepository eventRepo,
            BookingRepository bookingRepo,
            NotificationService notificationService, UserRepository userRepository
    ) {
        this.eventRepo = eventRepo;
        this.bookingRepo = bookingRepo;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Event updateEvent(Long eventId, EventRequest updatedEvent) {

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setEventDate(updatedEvent.getEventDate());

        Event saved = eventRepo.save(event);

        List<User> customers =
                bookingRepo.findByEventId(eventId)
                        .stream()
                        .map(Booking::getCustomer)
                        .distinct()
                        .toList();

        notificationService.notifyEventUpdate(saved, customers);

        return saved;
    }

    public Event addEvent(EventRequest eventRequest){
        User organiser = userRepository.findById(eventRequest.getOrganizer())
                .orElseThrow(()->new ResourceNotFoundException("Organiser not found"));
        Event event = new Event();
        eventRequest.setEventDate(eventRequest.getEventDate());
        event.setTitle(eventRequest.getTitle());
        event.setDescription(event.getDescription());
        event.setOrganizer(organiser);
        event.setAvailableSeats(event.getAvailableSeats());
        event.setTotalSeats(event.getTotalSeats());
        return eventRepo.save(event);
    }
}
