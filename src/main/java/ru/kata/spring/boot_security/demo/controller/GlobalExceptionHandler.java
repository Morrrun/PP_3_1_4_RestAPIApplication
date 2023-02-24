package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.util.exception.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.util.exception.validators.ValidError;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException err) {
        UserErrorResponse response = new UserErrorResponse(err.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ValidError> handleException(MethodArgumentNotValidException err) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = err.getBindingResult().getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        ValidError response = new ValidError(errorMsg.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
