package com.eventbooking.auth;

import com.eventbooking.security.JwtUtils;
import com.eventbooking.user.UserRegistrationDto;
import com.eventbooking.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.eventbooking.exception.ResponseGenerator.generateResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );

        return jwtUtil.generateToken(
                (UserDetails) authentication.getPrincipal()
        );
    }
    @PostMapping("/register-customer-user")
    public ResponseEntity<Object> registerCustomerUser(@RequestBody UserRegistrationDto user) {
        userService.registerUser(user);
        return generateResponse("Registered successfully", HttpStatus.CREATED, user);
    }
}

