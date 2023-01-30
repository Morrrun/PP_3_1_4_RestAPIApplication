package ru.kata.spring.boot_security.demo.DTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


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
    @NotBlank(message = "Укажите возраст!")
    private int age;

    @NotBlank(message = "Укажите Email!")
    @Size(min = 10, max = 30, message = "Email должнен быть от 10 до 30 символов длинной")
    private String email;

    @Size(min = 10, max = 100, message = "Пароль должен быть от 5 до 100 символов длинной")
    @NotBlank(message = "Укажите пароль!")
    private String password;

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
