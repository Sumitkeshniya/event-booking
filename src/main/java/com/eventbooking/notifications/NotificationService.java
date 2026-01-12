package com.eventbooking.notifications;

import com.eventbooking.event.Event;
import com.eventbooking.user.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Async
    public void sendBookingConfirmation(User customer, Event event) {
        System.out.println(
                "EMAIL: Booking confirmed for " + customer.getEmail() +
                        " for event: " + event.getTitle()
        );
    }

    @Async
    public void notifyEventUpdate(Event event, List<User> customers) {
        customers.forEach(c ->
                System.out.println(
                        "NOTIFICATION: Event updated :- " +
                                event.getTitle() + " | User: " + c.getEmail()
                )
        );
    }
}
