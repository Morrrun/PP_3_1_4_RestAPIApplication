package ru.kata.spring.boot_security.demo.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.controller.converters.UserMapper;
import ru.kata.spring.boot_security.demo.dao.abstracts.dto.UserDtoDao;
import ru.kata.spring.boot_security.demo.model.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.model.dto.UserDTO;
import ru.kata.spring.boot_security.demo.service.abstracts.model.RoleService;
import ru.kata.spring.boot_security.demo.service.abstracts.model.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController("/api")
@Api(value = "API для взаимодействия с клиентом")
public class RestAPIController {
    private static final String ADMIN_ROLE = "hasRole('ADMIN')";
    private final UserDtoDao userDtoDao;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAPIController(UserDtoDao userDtoDao, UserService userService, RoleService roleService) {
        this.userDtoDao = userDtoDao;
        this.userService = userService;
        this.roleService = roleService;
    }

    //Получаем весь список пользователей
    @ApiOperation(value = "Получение пользователей",
                  notes = "Предоставляет полный список пользователей с ролями, которые храняться в БД",
                  httpMethod = "GET",
                  response = UserDTO.class,
//                  produces = "application/json",
                  authorizations = @Authorization(ADMIN_ROLE))
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Пользователи успешно получены из БД", response = Set.class),
            @ApiResponse( code = 401, message = "Необходима аунтификация"),
            @ApiResponse( code = 403, message = "Доступ закрыт для неавтаризированного пользователя"),
            @ApiResponse( code = 404, message = "Пользователей нет в БД"),
            @ApiResponse( code = 500, message = "Ошибка на стороне сервера")
    })
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/users")
    public ResponseEntity<Set<UserDTO>> getUsers() {
//        Optional<UserDTO> userDTO3 = userDtoDao.getById(1L);
//        System.out.println(userDTO3.orElse(null));

//        Set<UserDTO> userDTOS = userDtoDao.getAll();
//        System.out.println(userDTOS);
//
//        for (UserDTO u : userDTOS) {
//            System.out.println(u);
//        }


        final Set<UserDTO> users = UserDTO.fromUserList(userService.getAllUsers());


        return !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Получаем одного пользователя по ID
    @ApiOperation(value = "Получение пользователя",
                  notes = "Предоставляет одного пользователя по ID, с ролями, которые храняться в БД",
                  httpMethod = "GET",
                  response = UserDTO.class,
                  authorizations = @Authorization(ADMIN_ROLE))
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Пользователь успешно получен из БД", response = UserDTO.class),
            @ApiResponse( code = 401, message = "Необходима аунтификация"),
            @ApiResponse( code = 403, message = "Доступ закрыт для не автаризированного пользователя"),
            @ApiResponse( code = 404, message = "Пользователя нет в БД"),
            @ApiResponse( code = 500, message = "Ошибка на стороне сервера")
    })
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {

        final UserDTO userDTO = UserDTO.fromUser(userService.getUser(id));

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    //Добавление нового пользователя
    @ApiOperation(value = "Регистрация пользователя",
                  notes = "Регистрирует нового пользователя с ролями которые храняться в БД",
                  httpMethod = "POST",
                  authorizations = @Authorization("PermitAll"))
    @ApiResponses( value = {
            @ApiResponse( code = 201, message = "Пользователь успешно создан"),
            @ApiResponse( code = 401, message = "Необходима аунтификация"),
            @ApiResponse( code = 403, message = "Доступ закрыт для не автаризированного пользователя"),
            @ApiResponse( code = 500, message = "Ошибка на стороне сервера")
    })
    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody
                                              UserDTO userDTO) {

        userService.addUser(UserMapper.INSTANCE.userDtoToUser(userDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    //Обновление пользователя
    @ApiOperation(value = "Изменение пользователя",
                  notes = "Изменяет данные пользователя и сохраняет в БД." +
                          "Ожидает установленного ID у пользователя, не изменённого со времени получения его из БД",
                  httpMethod = "PATCH",
                  authorizations = @Authorization(ADMIN_ROLE))
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "Пользователь успешно обновлён"),
            @ApiResponse( code = 401, message = "Необходима аунтификация"),
            @ApiResponse( code = 403, message = "Доступ закрыт для не автаризированного пользователя"),
            @ApiResponse( code = 500, message = "Ошибка на стороне сервера")
    })
    @PreAuthorize(ADMIN_ROLE)
    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody
                                           UserDTO userDTO) {

        userService.updateUser(UserMapper.INSTANCE.userDtoToUser(userDTO));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Удаление пользователя
    @ApiOperation(value = "Удаление пользователя",
                  notes = "Удаляет пользователя по ID из БД." +
                          "Ожидает установленного ID у пользователя, не изменённого со времени получения его из БД",
                  httpMethod = "DELETE",
                  authorizations = @Authorization(ADMIN_ROLE))
    @ApiResponses( value = {
            @ApiResponse( code = 204, message = "Пользователь успешно удалён"),
            @ApiResponse( code = 401, message = "Необходима аунтификация"),
            @ApiResponse( code = 403, message = "Доступ закрыт для не автаризированного пользователя"),
            @ApiResponse( code = 500, message = "Ошибка на стороне сервера")
    })
    @PreAuthorize(ADMIN_ROLE)
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") int id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Передача ролей
    @ApiOperation(value = "Получение ролей",
                  notes = "Предоставляет все роли хранящиеся в БД",
                  httpMethod = "GET",
                  response = RoleDTO.class,
                  authorizations = @Authorization(ADMIN_ROLE))
    @ApiResponses( value = {
            @ApiResponse( code = 200, message = "Роли успешно получены из БД", response = Set.class),
            @ApiResponse( code = 401, message = "Необходима аунтификация"),
            @ApiResponse( code = 403, message = "Доступ закрыт для не автаризированного пользователя"),
            @ApiResponse( code = 404, message = "Ролей нет в БД"),
            @ApiResponse( code = 500, message = "Ошибка на стороне сервера")
    })
    @PreAuthorize(ADMIN_ROLE)
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRole() {
        final List<RoleDTO> rolesDTO = RoleDTO.fromRoleList(roleService.getRoles());

        return !rolesDTO.isEmpty()
                ? new ResponseEntity<>(rolesDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
