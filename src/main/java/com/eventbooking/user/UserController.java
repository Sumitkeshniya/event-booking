package com.eventbooking.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eventbooking.exception.ResponseGenerator.generateResponse;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register-customer-user")
    public ResponseEntity<Object> registerCustomerUser( @RequestBody UserRegistrationDto user) {
        userService.registerUser(user);
        return generateResponse("Registered successfully", HttpStatus.CREATED, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> registerCustomerUser( @PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id,user);
        return generateResponse("Updated successfully", HttpStatus.CREATED, user);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getUser() {
        return generateResponse("Data", HttpStatus.CREATED, userService.getAll());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginRequest loginRequest) {
        var user = userService.login(loginRequest);
        return generateResponse("Login Successfully", user);
    }

}
