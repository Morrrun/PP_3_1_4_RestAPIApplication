package ru.kata.spring.boot_security.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class JSONObjectDTO {
    private UserDTO userDTO;

    private Set<RoleDTO> rolesDTO;

}
