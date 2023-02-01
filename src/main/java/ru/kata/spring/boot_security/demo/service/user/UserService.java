package ru.kata.spring.boot_security.demo.service.user;

import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User loadUserByEmail(String email);
    List<UserDTO> getAllUsersDTO();
    UserDTO getUserDTO(long id);
    void addUserDTO(UserDTO userDTO);
    void updateUserDTO(UserDTO userDTO, int id);
    void deleteUser(long id);
}
