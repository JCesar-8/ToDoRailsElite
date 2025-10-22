package com.todo.rails.elite.starter.code.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// TODO 15: Add Global Exception Handling. Use @ControllerAdvice and @ExceptionHandler to handle exceptions like EntityNotFoundException.
@ControllerAdvice
public class Globalexceptionhandling {
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

