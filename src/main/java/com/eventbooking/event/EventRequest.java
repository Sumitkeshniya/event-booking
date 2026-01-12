package com.eventbooking.event;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private int totalSeats;
    private int availableSeats;
    private long organizer;
}
