package ru.kata.spring.boot_security.demo.initdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DataInitService {
    public final UserService userService;
    public final RoleService roleService;
    public final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitService(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createEntity() {
        createRole();
        createUser();
    }

    private void createUser() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Michail");
        user.setLastName("Alexsandrov");
        user.setEmail("mifon.199708@gmail.com");
        user.setAge(25);
        user.setPassword("12345");
        user.setRoles(new HashSet<>(roles));

        userService.addUser(user);
    }

    List<Role> roles = new ArrayList<>();
    private void createRole() {
        Role roleAdmin = new Role("ADMIN");
        roleAdmin.setId(1);

        Role roleUser = new Role("USER");
        roleUser.setId(2);

        roles.add(roleAdmin);
        roles.add(roleUser);

        roleService.saveAllAndFlush(roles);
    }
}
