package com.eventbooking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ControllerAdvice
public class GlobalException {

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }
    public static class DatabaseException extends RuntimeException {
        public DatabaseException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidAppointmentException extends RuntimeException {
        public InvalidAppointmentException(String message) {
            super(message);
        }
    }

    // Handle DatabaseException
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleDatabaseException(DatabaseException ex) {
        return ResponseGenerator.generateResponse("Database error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    // Handle InvalidAppointmentException
    @ExceptionHandler(InvalidAppointmentException.class)
    public ResponseEntity<Object> handleInvalidAppointmentException(InvalidAppointmentException ex) {
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }


    // Handle validation exceptions for @Valid annotated fields
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract field errors and add to map with concise messages
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // Return a custom response with just the validation error messages
        return ResponseGenerator.generateResponse("Validation failed", HttpStatus.BAD_REQUEST, errors);
    }


    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<Object> handleDuplicateEntryException(DuplicateEntryException ex) {
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.CONFLICT, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String userMessage;

        // Check for Duplicate Entry
        Pattern duplicatePattern = Pattern.compile("Duplicate entry '(.*?)'");
        Matcher duplicateMatcher = duplicatePattern.matcher(rootMessage);
        if (duplicateMatcher.find()) {
            userMessage = "Duplicate data found: '" + duplicateMatcher.group(1) + "'";
        }
        // Check for Foreign Key Constraint Violation
        else if (rootMessage.contains("foreign key constraint fails")) {
            userMessage = "Cannot delete or update: the record is referenced in another table.";
        }
        // Fallback message
        else {
            userMessage = "Data integrity violation: " + rootMessage;
        }

        return ResponseGenerator.generateResponse(userMessage, HttpStatus.CONFLICT, null);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(AlreadyCheckedInException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(AlreadyCheckedInException ex) {
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    // Handle other general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        System.out.println(ex);
        return ResponseGenerator.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

}
