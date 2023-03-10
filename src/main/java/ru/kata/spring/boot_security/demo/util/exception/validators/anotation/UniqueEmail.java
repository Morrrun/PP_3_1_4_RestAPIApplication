package ru.kata.spring.boot_security.demo.util.exception.validators.anotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Человек с таким email уже зарегестрирован";

    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
