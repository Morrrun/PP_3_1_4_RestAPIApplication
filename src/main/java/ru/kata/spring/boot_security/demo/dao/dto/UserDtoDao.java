package ru.kata.spring.boot_security.demo.dao.dto;

import ru.kata.spring.boot_security.demo.model.dto.UserDTO;

import java.util.Optional;
import java.util.Set;

public interface UserDtoDao {
    Set<UserDTO> getAll();
    Optional<UserDTO> getById(Long userId);
}
