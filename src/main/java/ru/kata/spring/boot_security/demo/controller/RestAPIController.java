package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.DTO.JSONObjectDTO;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;
import ru.kata.spring.boot_security.demo.util.Exception.UserNotCreatedException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController("/api")
public class RestAPIController {
    private final Validator userValidator;
    private final Validator registrationValidator;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAPIController(@Qualifier("userValidator") Validator userValidator,
                             @Qualifier("registrationValidator") Validator registrationValidator, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                             UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.registrationValidator = registrationValidator;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    //Получаем весь список пользователей
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<JSONObjectDTO>> getUsers() {
        final List<JSONObjectDTO> users = userService.getAllUsers().stream()
                .map(this::converterToJSONObjectDTO)
                .collect(Collectors.toList());


        return !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Получаем одного пользователя по ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{id}")
    public ResponseEntity<JSONObjectDTO> getUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);

        final JSONObjectDTO jsonObjectDTO = converterToJSONObjectDTO(user);

        return new ResponseEntity<>(jsonObjectDTO, HttpStatus.OK);
    }


    //Добавление нового пользователя
    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody
                                              JSONObjectDTO jsonObjectDTO,
                                              BindingResult bindingResult) {

        registrationValidator.validate(jsonObjectDTO, bindingResult);
        detectedError(bindingResult);

        userService.addUser(converterToUser(jsonObjectDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Обновление пользователя
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/people/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id,
                                        @RequestBody
                                        JSONObjectDTO jsonObjectDTO,
                                        BindingResult bindingResult) {

        userValidator.validate(jsonObjectDTO, bindingResult);
        detectedError(bindingResult);

        User user = converterToUser(jsonObjectDTO);
        user.setId(id);

        userService.updateUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Удаление пользователя
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRole() {
        final List<RoleDTO> rolesDTO = roleService.getRoles().stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .toList();

        return !rolesDTO.isEmpty()
                ? new ResponseEntity<>(rolesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Метод для преобразования в User
    private User converterToUser(JSONObjectDTO jsonObjectDTO) {
        User user = modelMapper.map(jsonObjectDTO.getUserDTO(), User.class);

        if (user.getId() == 0) {
            for (RoleDTO role : jsonObjectDTO.getRolesDTO()) {
                if (role.getRole().equals("ADMIN")) {
                    Role userRole = roleService.findByRole(role.getRole());
                    user.addRoleToUser(userRole);
                }
            }
        } else {
            User checkUser = userService.getUser(user.getId());
            user.setRoles(checkUser.getRoles());
            Role userRole = null;

            for (RoleDTO role : jsonObjectDTO.getRolesDTO()) {
                if (role.getRole().equals("ADMIN")) {
                    userRole = roleService.findByRole(role.getRole());
                }
            }
            if (userRole != null) {
                if (!checkUser.getRoles().contains(userRole)) {
                    user.addRoleToUser(userRole);
                }
            } else {
                userRole = roleService.findByRole("ADMIN");
                if(checkUser.getRoles().contains(userRole)) {
                    user.getRoles().remove(userRole);
                }
            }


            if (!user.getPassword().isEmpty()) {
                if (!checkUser.getPassword().equals(user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            } else {
                user.setPassword(checkUser.getPassword());
            }

        }
        return user;
    }

    //Метод для преобразования в JSONObjectDTO
    private JSONObjectDTO converterToJSONObjectDTO(User user) {
        JSONObjectDTO obj = new JSONObjectDTO();
        RoleDTO roleDTO;

        obj.setUserDTO(modelMapper.map(user, UserDTO.class));
        Set<RoleDTO> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleDTO = modelMapper.map(role, RoleDTO.class);
            roles.add(roleDTO);
        }

        obj.setRolesDTO(roles);

        return obj;
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
