package ru.kata.spring.boot_security.demo.service.abstracts.model;

import ru.kata.spring.boot_security.demo.model.entity.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);
    List<Role> getRoles();
    void saveAllAndFlush(List<Role> roles);
}
