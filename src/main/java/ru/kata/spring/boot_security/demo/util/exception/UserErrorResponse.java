package ru.kata.spring.boot_security.demo.util.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserErrorResponse {
    private String message;

}
