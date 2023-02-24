package ru.kata.spring.boot_security.demo.service.user;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

public interface UserService {
    User loadUserByEmail(String email);
    Set<User> getAllUsers();
    User getUser(long id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
}
