package ru.kata.spring.boot_security.demo.model.dto;


import ru.kata.spring.boot_security.demo.model.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public record RoleDTO(Long id, String role) {
    public static RoleDTO fromUser(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getRole()
        );
    }
    public static List<RoleDTO> fromRoleList(List<Role> roleSet) {
        return roleSet.stream()
                .map(RoleDTO::fromUser)
                .collect(Collectors.toList());
    }
}