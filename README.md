🎟️ Event Booking System – Backend

A Spring Boot–based backend application for managing events and bookings with JWT authentication, role-based access control, Swagger API documentation, and asynchronous background processing.

🚀 Features

Role-based access (ORGANIZER, CUSTOMER)

JWT-based authentication (stateless)

Secure password storage using BCrypt

Swagger (OpenAPI) documentation

Async background tasks

Clean controller–service–repository architecture

🧱 Tech Stack

Java 17

Spring Boot 3.x

Spring Security

JWT (jjwt 0.11.5)

Spring Data JPA

springdoc-openapi

 MySQL

👥 User Roles
Event Organizer

Create events

Update events

View owned events

Customer

Browse events

Book tickets

View bookings

Access to APIs is enforced using Spring Security + JWT roles.

🔐 Authentication & Authorization

Login returns a JWT token

JWT must be sent in request headers

Roles are embedded inside the token
