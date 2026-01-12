package com.eventbooking.booking;

import lombok.Data;

@Data
public class BookingRequest {
    private Long eventId;
    private Long userId;
    private int seats;
}
