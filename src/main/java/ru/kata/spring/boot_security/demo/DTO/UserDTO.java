package ru.kata.spring.boot_security.demo.DTO;

import lombok.*;
import ru.kata.spring.boot_security.demo.util.Exception.validators.anotation.UniqueEmail;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDTO {

    private long id;

    @NotBlank(message = "Укажите имя!")
    @Size(min = 2, max = 30, message = "Имя должно быть от 2-х до 30 символов длинной")
    private String firstName;

    @Size(min = 2, max = 30, message = "Фамилия должна быть от 2-х до 30 символов длинной")
    @NotBlank(message = "Укажите фамилию!")
    private String lastName;

    @Max(value = 120, message = "Мы регистрируем людей в возрасте от 8 до 120 лет")
    @Min(value = 8, message = "Мы регистрируем людей в возрасте от 8 до 120 лет")
    private int age;


    @NotBlank(message = "Укажите Email!")
    @UniqueEmail
    @Size(min = 10, max = 30, message = "Email должнен быть от 10 до 30 символов длинной")
    private String email;

    @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов длинной")
    @NotBlank(message = "Укажите пароль!")
    private String password;

    private Set<RoleDTO> roles;

}
