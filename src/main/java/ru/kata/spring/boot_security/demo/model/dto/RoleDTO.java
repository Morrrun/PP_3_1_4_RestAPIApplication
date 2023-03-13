package ru.kata.spring.boot_security.demo.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO {
    private Long id;
    private String role;

}