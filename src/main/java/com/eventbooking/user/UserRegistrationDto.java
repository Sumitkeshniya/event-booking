package com.eventbooking.user;

import lombok.Data;

@Data
public class UserRegistrationDto  {
    private String firstName;

    private String lastName;

    private String contactNo;

    private String email;

    private String password;

    private Role role;
}
