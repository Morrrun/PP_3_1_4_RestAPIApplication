package ru.kata.spring.boot_security.demo.model.dto;

import ru.kata.spring.boot_security.demo.model.entity.User;
import ru.kata.spring.boot_security.demo.util.exception.validators.anotation.UniqueEmail;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public record UserDTO(Long id,
                      @NotBlank(message = "Укажите имя!")
                      @Size(min = 2, max = 30, message = "Имя должно быть от 2-х до 30 символов длинной")
                      String firstName,
                      @Size(min = 2, max = 30, message = "Фамилия должна быть от 2-х до 30 символов длинной")
                      @NotBlank(message = "Укажите фамилию!")
                      String lastName,
                      @Max(value = 120, message = "Мы регистрируем людей в возрасте от 8 до 120 лет")
                      @Min(value = 8, message = "Мы регистрируем людей в возрасте от 8 до 120 лет")
                      int age,
                      @NotBlank(message = "Укажите Email!")
                      @UniqueEmail
                      @Size(min = 10, max = 30, message = "Email должнен быть от 10 до 30 символов длинной")
                      String email,
                      @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов длинной")
                      @NotBlank(message = "Укажите пароль!")
                      String password,
                      List<RoleDTO> roles) {

    public static UserDTO fromUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                RoleDTO.fromRoleList(user.getRoles())
        );
    }

    public static Set<UserDTO> fromUserList(Set<User> userSet) {
        return userSet.stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toSet());
    }
}
