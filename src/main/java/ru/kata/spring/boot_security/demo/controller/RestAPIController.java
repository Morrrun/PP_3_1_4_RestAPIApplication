package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.model.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController("/api")
public class RestAPIController {
    private static final String ADMIN_ROLE = "hasRole('ADMIN')";
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestAPIController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    //Получаем весь список пользователей
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/users")
    public ResponseEntity<Set<UserDTO>> getUsers() {
        final Set<UserDTO> users = userService.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toSet());


        return !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Получаем одного пользователя по ID
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {

        final UserDTO userDTO = modelMapper.map(userService.getUser(id), UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    //Добавление нового пользователя
    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody
                                              UserDTO userDTO) {

        userService.addUser(modelMapper.map(userDTO, User.class));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    //Обновление пользователя
    @PreAuthorize(ADMIN_ROLE)
    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody
                                           UserDTO userDTO) {

        userService.updateUser(modelMapper.map(userDTO, User.class));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Удаление пользователя
    @PreAuthorize(ADMIN_ROLE)
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") int id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Передача ролей
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRole() {
        final List<RoleDTO> rolesDTO = roleService.getRoles().stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .toList();

        return !rolesDTO.isEmpty()
                ? new ResponseEntity<>(rolesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
