package ru.kata.spring.boot_security.demo.dao.dto;

import ru.kata.spring.boot_security.demo.model.dto.UserDTO;

import java.util.Optional;

public interface UserDtoDao {
    Optional<UserDTO> getById(Long userId);
}
