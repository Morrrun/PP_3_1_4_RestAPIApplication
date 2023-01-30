package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.util.Exception.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.Exception.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.Exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException err) {
        UserErrorResponse response = new UserErrorResponse(
                err.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException err) {
        UserErrorResponse response = new UserErrorResponse(
                err.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
