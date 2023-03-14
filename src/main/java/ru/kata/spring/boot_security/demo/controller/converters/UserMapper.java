package ru.kata.spring.boot_security.demo.controller.converters;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kata.spring.boot_security.demo.model.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDTO userDTO);
}
