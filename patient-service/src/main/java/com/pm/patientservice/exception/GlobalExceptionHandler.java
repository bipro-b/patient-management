package com.pm.patientservice.exception;


import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);

    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {

        logger.warn("Email already exists: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Email already exists");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFound(PatientNotFoundException ex) {

        logger.warn("Patient not found: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Patient not found");
        return ResponseEntity.status(404).body(error);
    }
}
