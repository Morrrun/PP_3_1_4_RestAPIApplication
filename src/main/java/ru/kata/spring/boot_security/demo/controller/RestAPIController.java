package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;
import ru.kata.spring.boot_security.demo.util.Exception.UserNotCreatedException;

import javax.validation.Valid;
import java.util.List;

@RestController("/api")
public class RestAPIController {
    private static final String ADMIN_ROLE = "hasRole('ADMIN')";
    private final Validator userValidator;
    private final Validator registrationValidator;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAPIController(@Qualifier("userValidator") Validator userValidator,
                             @Qualifier("registrationValidator") Validator registrationValidator,
                             UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.registrationValidator = registrationValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

    //Получаем весь список пользователей
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        final List<UserDTO> users = userService.getAllUsersDTO();


        return !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Получаем одного пользователя по ID
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {

        final UserDTO userDTO = userService.getUserDTO(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    //Добавление нового пользователя
    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody
                                              @Valid
                                              UserDTO userDTO,
                                              BindingResult bindingResult) {

        registrationValidator.validate(userDTO, bindingResult);
        detectedError(bindingResult);

        userService.addUserDTO(userDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Обновление пользователя
    @PreAuthorize(ADMIN_ROLE)
    @PatchMapping("/people/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id,
                                        @RequestBody
                                        @Valid
                                        UserDTO userDTO,
                                        BindingResult bindingResult) {

        userValidator.validate(userDTO, bindingResult);
        detectedError(bindingResult);

        userService.updateUserDTO(userDTO, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Удаление пользователя
    @PreAuthorize(ADMIN_ROLE)
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRole() {
        final List<RoleDTO> rolesDTO = roleService.getRolesDTO();

        return !rolesDTO.isEmpty()
                ? new ResponseEntity<>(rolesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Метод для валидации данных
    private void detectedError(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        }
    }

}
