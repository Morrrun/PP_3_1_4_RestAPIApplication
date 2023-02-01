package ru.kata.spring.boot_security.demo.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.user.UserService;
import ru.kata.spring.boot_security.demo.service.user.UserServiceImpl;

@Component
public class RegistrationValidator implements Validator {
    private final UserService userService;

    @Autowired
    public RegistrationValidator(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userObj = (UserDTO) target;

        User userWithEmail = userService.loadUserByEmail(userObj.getEmail());

        //Валидируем имя
        if (userObj.getFirstName().isBlank()) {
            errors.rejectValue("firstName", "", "Укажите имя!");

        } else if (userObj.getFirstName().length() > 30 || userObj.getFirstName().length() < 2) {
            errors.rejectValue("firstName", "",
                    "Имя должно быть от 2-х до 30 символов длинной");
        }

        //Валидируем фамилию
        if (userObj.getLastName().isBlank()) {
            errors.rejectValue("lastName", "", "Укажите фамилию!");

        } else if (userObj.getLastName().length() > 30 || userObj.getLastName().length() < 2) {
            errors.rejectValue("lastName", "",
                    "Фамилия должна быть от 2-х до 30 символов длинной");
        }

        //Валидируем возраст
        if(userObj.getAge() == 0) {
            errors.rejectValue("age", "", "Укажите возраст!");

        } else if (userObj.getAge() < 8 || userObj.getAge() > 120)
            errors.rejectValue("age", "", "Мы регистрируем людей в возрасте от 8 до 120 лет");

        //Валидируем email
        if (userObj.getEmail().isEmpty()) {
            errors.rejectValue("email", "", "Укажите email!");

        } else if (userWithEmail != null) {
            errors.rejectValue("email", "",
                    "Человек с таким email уже зарегестрирован!");

        } else if (userObj.getEmail().length() < 10 || userObj.getEmail().length() > 30 ) {
            errors.rejectValue("email", "",
                    "Email должнен быть от 10 до 30 символов длинной");
        }

        //Валидируем пароль
        if (userObj.getPassword().isEmpty()) {
            errors.rejectValue("password", "", "Укажите пароль!");

        } else if (userObj.getPassword().length() > 100 || userObj.getPassword().length() < 5) {
            errors.rejectValue("password", "",
                    "Пароль должен быть от 5 до 100 символов длинной");
        }


    }
}
