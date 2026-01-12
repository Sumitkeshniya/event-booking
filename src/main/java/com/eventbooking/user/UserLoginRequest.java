package com.eventbooking.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotEmpty(message = "Contact number is required")
    private String contactNo;

    @NotEmpty(message = "Password is required")
    private String password;

}
