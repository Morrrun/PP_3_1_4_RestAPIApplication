package ru.kata.spring.boot_security.demo.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO {
    private int id;
    private String role;
}